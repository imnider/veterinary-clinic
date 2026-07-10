import { HttpClient } from '@angular/common/http';
import { inject, Injectable } from '@angular/core';
import { Observable, of } from 'rxjs';
import { environment } from '../../../environment/environment';
import { ApiResponse } from '../interfaces/models/api-response.interface';
import { AppointmentSummary } from '../interfaces/entities/appointment.interface';

@Injectable({ providedIn: 'root' })
export class AppointmentService {
  private readonly http = inject(HttpClient);
  private readonly apiUrl = `${environment.apiUrl}/appointments`;

  getUpcoming(): Observable<ApiResponse<AppointmentSummary[]>> {
    return of({ success: true, message: null, data: [] });
  }
}
