export class Location {

  id?: number;
  country: string;
  city: string;
  postalCode: string;
  address: string;
  name: string;
  numberOfAvailableVaccines: number;
  vaccineName: string;

  constructor(id: number,
              country: string,
              city: string,
              postalCode: string,
              address: string,
              name: string,
              numberOfAvailableVaccines: number,
              vaccineName: string) {
    this.id = id;
    this.country = country;
    this.city = city;
    this.postalCode = postalCode;
    this.address = address;
    this.name = name;
    this.numberOfAvailableVaccines = numberOfAvailableVaccines;
    this.vaccineName = vaccineName;
  }


}
