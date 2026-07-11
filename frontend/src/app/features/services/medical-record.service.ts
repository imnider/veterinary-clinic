import { Injectable, inject } from '@angular/core';
import { HttpClient } from '@angular/common/http';
import { Observable } from 'rxjs';
import { environment } from '../../../environment/environment';
import { ApiResponse } from '../interfaces/models/api-response.interface';
import {
  CreateMedicalRecordRequest,
  MedicalRecordResponse,
} from '../interfaces/entities/medical-record.interface';

@Injectable({ providedIn: 'root' })
export class MedicalRecordService {
  private http = inject(HttpClient);
  private readonly baseUrl = `${environment.apiUrl}/records`;

  createMedicalRecord(
    request: CreateMedicalRecordRequest,
  ): Observable<ApiResponse<MedicalRecordResponse>> {
    return this.http.post<ApiResponse<MedicalRecordResponse>>(this.baseUrl, request);
  }

  getAllMedicalRecords(): Observable<ApiResponse<MedicalRecordResponse[]>> {
    return this.http.get<ApiResponse<MedicalRecordResponse[]>>(this.baseUrl);
  }

  getMedicalRecordsByPet(petId: number): Observable<ApiResponse<MedicalRecordResponse[]>> {
    return this.http.get<ApiResponse<MedicalRecordResponse[]>>(`${this.baseUrl}/pets/${petId}`);
  }

  getMedicalRecordByAppointment(
    appointmentId: number,
  ): Observable<ApiResponse<MedicalRecordResponse>> {
    return this.http.get<ApiResponse<MedicalRecordResponse>>(
      `${this.baseUrl}/appointments/${appointmentId}`,
    );
  }
}
