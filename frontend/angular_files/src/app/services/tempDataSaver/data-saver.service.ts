import { Injectable } from '@angular/core';

@Injectable({
  providedIn: 'root'
})
export class DataSaverService {

  private dataToHold: any;

  setData(data: any) {
    this.dataToHold = data;
  }

  getData() {
    return this.dataToHold;
  }

  clearData() {
    this.dataToHold = null;
  }

}
