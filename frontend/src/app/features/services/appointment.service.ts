import { Injectable, inject } from '@angular/core';
import { HttpClient } from '@angular/common/http';
import { Observable } from 'rxjs';
import { environment } from '../../../environment/environment';
import { ApiResponse } from '../interfaces/models/api-response.interface';
import {
  AppointmentResponse,
  CreateAppointmentRequest,
  UpdateAppointmentStatusRequest,
} from '../interfaces/entities/appointment.interface';

@Injectable({ providedIn: 'root' })
export class AppointmentService {
  private http = inject(HttpClient);
  private readonly baseUrl = `${environment.apiUrl}/appointments`;

  createAppointment(
    request: CreateAppointmentRequest,
  ): Observable<ApiResponse<AppointmentResponse>> {
    return this.http.post<ApiResponse<AppointmentResponse>>(this.baseUrl, request);
  }

  getAppointments(pendingOnly = false): Observable<ApiResponse<AppointmentResponse[]>> {
    const url = pendingOnly ? `${this.baseUrl}/pending` : this.baseUrl;
    return this.http.get<ApiResponse<AppointmentResponse[]>>(url);
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

  updateAppointmentStatus(
    appointmentId: number,
    request: UpdateAppointmentStatusRequest,
  ): Observable<ApiResponse<AppointmentResponse>> {
    return this.http.patch<ApiResponse<AppointmentResponse>>(
      `${this.baseUrl}/${appointmentId}/status`,
      request,
    );
  }
}
