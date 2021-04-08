export class Location {

  id?: number;
  country: string;
  city: string;
  postalCode: string;
  address: string;
  name: string;
  numberOfAvailableVaccines: number;
  vaccinationBrandt: string;

  constructor(id: number,
              country: string,
              city: string,
              postalCode: string,
              address: string,
              name: string,
              numberOfAvailableVaccines: number,
              vaccinationBrandt: string) {
    this.id = id;
    this.country = country;
    this.city = city;
    this.postalCode = postalCode;
    this.address = address;
    this.name = name;
    this.numberOfAvailableVaccines = numberOfAvailableVaccines;
    this.vaccinationBrandt = vaccinationBrandt;
  }


}
