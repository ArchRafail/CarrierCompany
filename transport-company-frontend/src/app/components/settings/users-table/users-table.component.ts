import { Component, DestroyRef, inject, ViewChild } from '@angular/core';
import { PrimengTableFilterCustomMatchMode } from "../../../models/primeng-table-filter-custom-match-mode";
import { DROPDOWN_BOOL_OPTIONS, PRIMENG_TABLE_FILTER_DEBOUNCE_TIME } from "../../../constants/constants";
import { ConfirmationService, FilterMatchMode, FilterMetadata, MenuItem } from "primeng/api";
import { Table, TableLazyLoadEvent } from "primeng/table";
import { PageDto } from "../../../api/models/page-dto";
import { UserDto } from "../../../api/models/user-dto";
import { debounce, distinctUntilChanged, finalize, first, interval, Subject } from "rxjs";
import { UserHttpService } from "../../../api/services/user-http.service";
import { ToastService } from "../../../services/toast.service";
import { PRIMENG_TABLE_FILTER_MATCH_MODES_WITH_DEBOUNCE } from "../../../constants/primeng-constants";
import { primeNgTableFiltersToRequestParams } from "../../../helpers/PrimeNgHelper";
import { Pageable } from "../../../api/models/pageable";
import { takeUntilDestroyed } from "@angular/core/rxjs-interop";
import { Role } from "../../../api/models/role";
import { DialogService, DynamicDialogRef } from "primeng/dynamicdialog";
import { ChangeRoleDialogComponent } from "../change-role-dialog/change-role-dialog.component";
import { AuthService } from "../../../services/auth.service";


@Component({
  selector: 'app-users-table',
  templateUrl: './users-table.component.html',
  styleUrls: ['./users-table.component.scss'],
  providers: [ConfirmationService]
})
export class UsersTableComponent {
  private readonly destroyRef = inject(DestroyRef);
  private appChangeRoleDialogRef?: DynamicDialogRef;
  public readonly FilterMatchMode = FilterMatchMode;
  public readonly PrimengTableFilterCustomMatchMode = PrimengTableFilterCustomMatchMode;
  public readonly DROPDOWN_BOOL_OPTIONS = DROPDOWN_BOOL_OPTIONS;
  @ViewChild("table") private table!: Table;
  usersPage?: PageDto<UserDto>;
  loading = true;
  private tableLazyLoad$: Subject<TableLazyLoadEvent> = new Subject<TableLazyLoadEvent>();
  private filtersPreviousState?: {[key: string]: FilterMetadata};
  contextMenuItems: MenuItem[] = [];
  selectedUser!: UserDto;
  roles: string[] = Object.values(Role);
  currentUserId!: number;

  constructor(private userHttpService: UserHttpService,
              private toastService: ToastService,
              private confirmationService: ConfirmationService,
              private dialogService: DialogService,
              private authService: AuthService) {
    this.tableLazyLoad$
      .pipe(
        distinctUntilChanged(),
        debounce(event => {
          const initialFiltering = !this.filtersPreviousState;
          let shouldDebounce = Object.entries(event.filters as {[s: string]: FilterMetadata})
            .some(([key, filter]) =>
              filter && PRIMENG_TABLE_FILTER_MATCH_MODES_WITH_DEBOUNCE.includes(filter.matchMode!)
              && filter.value !== this.filtersPreviousState?.[key]?.value)
          return interval(!initialFiltering && shouldDebounce ? PRIMENG_TABLE_FILTER_DEBOUNCE_TIME : 0);
        })
      ).subscribe( event => {
      this.getAll(primeNgTableFiltersToRequestParams(event.filters), Pageable.fromPrimeNg(event));
      this.filtersPreviousState = structuredClone(event.filters as {[s: string]: FilterMetadata});
    });
    this.initContextMenu();
    this.currentUserId = this.authService.getUserId();
  }

  initContextMenu() {
    this.contextMenuItems = [
      {
        label: 'Change role',
        icon: 'pi pi-pencil',
        command: () => this.changeRoleDialog(),
        visible: this.selectedUser && this.selectedUser.id !== this.currentUserId
      },
      {
        label: 'Activate',
        icon: 'pi pi-check-circle',
        command: () => this.updateActive(true),
        visible: this.selectedUser && this.selectedUser.is_disabled && this.selectedUser.id !== this.currentUserId
      },
      {
        label: 'Deactivate',
        icon: 'pi pi-trash',
        command: () => this.confirmDeactivation(),
        visible: this.selectedUser && !this.selectedUser.is_disabled && this.selectedUser.id !== this.currentUserId,
        styleClass: "menuitem-danger"
      },
    ];
  }

  onLazyLoad(event: TableLazyLoadEvent) {
    this.loading = true;
    this.usersPage = undefined;
    this.tableLazyLoad$.next(event);
  }

  private getAll(filters: any = {}, pageable: Pageable = new Pageable()){
    this.userHttpService.getAll(filters, pageable)
      .pipe(
        finalize(() => this.loading = false),
        takeUntilDestroyed(this.destroyRef)
      )
      .subscribe({
        next: page => this.usersPage = page,
        error: this.toastService.handleHttpError
      });
  }

  private changeRoleDialog() {
    this.appChangeRoleDialogRef = this.dialogService.open(ChangeRoleDialogComponent, {
      data: { id: this.selectedUser!.id, role: this.selectedUser!.role },
      header: "Change user's role",
      width: '400px',
      baseZIndex: 100,
    });
    this.appChangeRoleDialogRef.onClose
      .pipe(first())
      .subscribe((newRole) => newRole && this.refresh());
  }

  private updateActive(isActive: boolean) {
    this.userHttpService.updateActive(this.selectedUser.id, isActive)
      .pipe(takeUntilDestroyed(this.destroyRef))
      .subscribe({
        next: () => {
          this.toastService.success(`User ${isActive ? 'activated' : 'deactivated'}`);
          this.refresh();
        },
        error: this.toastService.handleHttpError
      })
  }

  private confirmDeactivation() {
    this.confirmationService.confirm({
      message: 'Are you sure that you want to deactivate this user?',
      header: 'Confirmation',
      icon: 'pi pi-exclamation-triangle p-error',
      accept: () => this.updateActive(false)
    });
  }

  onContextMenuSelect(event: any) {
    this.initContextMenu();
  }

  refresh() {
    this.table.onLazyLoad.emit(this.table.createLazyLoadMetadata());
  }
}
