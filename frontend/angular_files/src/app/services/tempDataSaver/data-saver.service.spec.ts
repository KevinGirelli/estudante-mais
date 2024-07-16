import { TestBed } from '@angular/core/testing';

import { DataSaverService } from './data-saver.service';

describe('DataSaverService', () => {
  let service: DataSaverService;

  beforeEach(() => {
    TestBed.configureTestingModule({});
    service = TestBed.inject(DataSaverService);
  });

  it('should be created', () => {
    expect(service).toBeTruthy();
  });
});
