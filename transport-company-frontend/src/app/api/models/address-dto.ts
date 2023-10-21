export interface AddressDto {
  city: string,
  street: string,
  location: Location
}

export interface Location {
  latitude: number,
  longitude: number
}
