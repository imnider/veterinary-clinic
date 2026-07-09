import { HttpClient } from '@angular/common/http';
import { computed, inject, Injectable, signal } from '@angular/core';
import { Router } from '@angular/router';
import { Observable, tap } from 'rxjs';
import { TokenService } from './token.service';
import { environment } from '../../../environment/environment';
import { AuthUser } from '../interfaces/entities/auth-user.interface';
import { AuthResponse, LoginRequest } from '../interfaces/models/login.interface';
import { ApiResponse } from '../interfaces/models/api-response.interface';

@Injectable({ providedIn: 'root' })
export class AuthService {
  private readonly http = inject(HttpClient);
  private readonly router = inject(Router);
  private readonly tokenService = inject(TokenService);

  private readonly apiUrl = `${environment.apiUrl}/auth`;

  readonly currentUser = signal<AuthUser | null>(this.loadUserFromStorage());

  readonly isAuthenticated = computed(() => this.currentUser() !== null);

  login(credentials: LoginRequest): Observable<ApiResponse<AuthResponse>> {
    return this.http.post<ApiResponse<AuthResponse>>(`${this.apiUrl}/login`, credentials).pipe(
      tap((response) => {
        if (response.data?.token) {
          this.tokenService.saveToken(response.data.token);
          this.currentUser.set(this.buildUserFromToken(response.data.token));
        }
      }),
    );
  }

  logout(): void {
    this.tokenService.clearToken();
    this.currentUser.set(null);
    this.router.navigate(['/login']);
  }

  private loadUserFromStorage(): AuthUser | null {
    const token = this.tokenService.getToken();
    if (!token) return null;

    const user = this.buildUserFromToken(token);
    if (!user) {
      this.tokenService.clearToken();
      return null;
    }
    return user;
  }

  private buildUserFromToken(token: string): AuthUser | null {
    const payload = this.tokenService.decodeToken(token);
    if (!payload || this.tokenService.isTokenExpired(payload)) return null;

    return {
      id: Number(payload.sub),
      username: payload.username,
      roles: payload.roles,
      permissions: payload.permissions,
    };
  }
}
