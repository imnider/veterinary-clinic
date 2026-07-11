import { HttpClient } from '@angular/common/http';
import { inject, Injectable } from '@angular/core';
import { environment } from '../../../environment/environment';
import { ApiResponse } from '../interfaces/models/api-response.interface';
import { UserResponse } from '../interfaces/entities/user.interface';
import { Observable } from 'rxjs';

@Injectable({ providedIn: 'root' })
export class UserService {
  private http = inject(HttpClient);
  private readonly baseUrl = `${environment.apiUrl}/users`;

  getUsers(): Observable<ApiResponse<UserResponse[]>> {
    return this.http.get<ApiResponse<UserResponse[]>>(`${this.baseUrl}`);
  }
}
