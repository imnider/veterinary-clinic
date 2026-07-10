import { Injectable, inject } from '@angular/core';
import { HttpClient } from '@angular/common/http';
import { Observable } from 'rxjs';
import { environment } from '../../../environment/environment';
import { ApiResponse } from '../interfaces/models/api-response.interface';
import { MedicalRecordResponse } from '../interfaces/entities/medical-record.interface';

@Injectable({ providedIn: 'root' })
export class MedicalRecordService {
  private http = inject(HttpClient);
  private readonly baseUrl = `${environment.apiUrl}/records`;

  getMedicalRecordsByPet(petId: number): Observable<ApiResponse<MedicalRecordResponse[]>> {
    return this.http.get<ApiResponse<MedicalRecordResponse[]>>(`${this.baseUrl}/pets/${petId}`);
  }
}
