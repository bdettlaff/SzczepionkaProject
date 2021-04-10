export class Registration{

  pesel: string;
  identificator: string;
  zipcode: string;
  referral: number;
  email: string;

  constructor(pesel: string, identificator: string, zipcode: string,  referral: number, email: string) {
    this.pesel = pesel;
    this.identificator = identificator;
    this.zipcode = zipcode;
    this.referral = referral;
    this.email = email;
  }
}
