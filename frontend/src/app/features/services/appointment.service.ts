import { Injectable, inject } from '@angular/core';
import { HttpClient } from '@angular/common/http';
import { Observable, of } from 'rxjs';
import { environment } from '../../../environment/environment';
import { ApiResponse } from '../interfaces/models/api-response.interface';
import {
  AppointmentRequest,
  AppointmentResponse,
  AppointmentSummary,
} from '../interfaces/entities/appointment.interface';

@Injectable({ providedIn: 'root' })
export class AppointmentService {
  private http = inject(HttpClient);
  private readonly baseUrl = `${environment.apiUrl}/appointments`;

  createAppointment(request: AppointmentRequest): Observable<ApiResponse<AppointmentResponse>> {
    return this.http.post<ApiResponse<AppointmentResponse>>(this.baseUrl, request);
  }

  getAppointmentsByPet(
    petId: number,
    pendingOnly = false,
  ): Observable<ApiResponse<AppointmentResponse[]>> {
    const url = pendingOnly
      ? `${this.baseUrl}/pets/${petId}/pending`
      : `${this.baseUrl}/pets/${petId}`;
    return this.http.get<ApiResponse<AppointmentResponse[]>>(url);
  }

  getUpcoming(): Observable<ApiResponse<AppointmentSummary[]>> {
    return of({ success: true, message: null, data: [] });
  }
}
