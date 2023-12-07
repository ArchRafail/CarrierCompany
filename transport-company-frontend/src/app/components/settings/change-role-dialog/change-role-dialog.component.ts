import { Component, DestroyRef, inject } from '@angular/core';
import { Role } from "../../../api/models/role";
import { UserHttpService } from "../../../api/services/user-http.service";
import { DynamicDialogConfig, DynamicDialogRef } from "primeng/dynamicdialog";
import { ToastService } from "../../../services/toast.service";
import { finalize } from "rxjs";
import { takeUntilDestroyed } from "@angular/core/rxjs-interop";


@Component({
  selector: 'app-change-role-dialog',
  templateUrl: './change-role-dialog.component.html',
  styleUrls: ['./change-role-dialog.component.scss']
})
export class ChangeRoleDialogComponent {
  private readonly destroyRef = inject(DestroyRef);
  id!: number;
  role?: Role;
  roles: string[] = Object.values(Role);
  saving = false;

  constructor(private userHttpService: UserHttpService,
              private toastService: ToastService,
              public dialogConfig: DynamicDialogConfig<{id: number, role: Role}>,
              public dialogRef: DynamicDialogRef) {
    this.id = this.dialogConfig.data!.id;
    this.role = dialogConfig.data!.role;
  }

  onSubmit() {
    this.saving = true;
    this.userHttpService.changeRole(this.id, this.role!)
      .pipe(
        finalize(() => this.saving = false),
        takeUntilDestroyed(this.destroyRef)
      )
      .subscribe({
        next: () => {
          this.toastService.success("Роль користувача змінено");
          this.dialogRef.close(true);
        },
        error: this.toastService.handleHttpError
      })
  }
}
