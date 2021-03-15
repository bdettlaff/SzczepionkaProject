export class Registration{

  pesel: string;
  identificator: string;
  zipcode: string;
  referral: number;

  constructor(pesel: string, identificator: string, zipcode: string,  referral: number) {
    this.pesel = pesel;
    this.identificator = identificator;
    this.zipcode = zipcode;
    this.referral = referral;
  }
}
