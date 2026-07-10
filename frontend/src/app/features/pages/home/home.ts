import { Component, computed, inject, signal } from '@angular/core';
import { AuthService } from '../../services/auth.service';
import { NgIcon } from '@ng-icons/core';
import { AppointmentService } from '../../services/appointment.service';
import { AppointmentSummary } from '../../interfaces/entities/appointment.interface';
import { AppointmentCardComponent } from '../../../shared/ui/appointment-card/appointment-card';

@Component({
  selector: 'app-home',
  standalone: true,
  imports: [AppointmentCardComponent, NgIcon],
  templateUrl: './home.html',
})
export class Home {
  private readonly authService = inject(AuthService);
  private readonly appointmentService = inject(AppointmentService);

  readonly username = computed(() => this.authService.currentUser()?.username ?? '');
  readonly upcomingAppointments = signal<AppointmentSummary[]>([]);

  constructor() {
    this.appointmentService.getUpcoming().subscribe((response) => {
      this.upcomingAppointments.set(response.data ?? []);
    });
  }
}
