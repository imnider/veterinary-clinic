import { Component, Input } from '@angular/core';
import { AppointmentSummary } from '../../../features/interfaces/entities/appointment.interface';
import { DatePipe } from '@angular/common';

@Component({
  selector: 'app-appointment-card',
  standalone: true,
  templateUrl: './appointment-card.html',
  imports: [DatePipe],
})
export class AppointmentCardComponent {
  @Input({ required: true }) appointment!: AppointmentSummary;
}
