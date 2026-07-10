import { Component, computed, ElementRef, inject, signal, ViewChild } from '@angular/core';
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

  @ViewChild('carousel')
  carousel!: ElementRef<HTMLDivElement>;

  readonly username = computed(() => this.authService.currentUser()?.username ?? '');
  readonly upcomingAppointments = signal<AppointmentSummary[]>([]);

  readonly banners = [
    'assets/images/banner-1.jpg',
    'assets/images/banner-2.jpg',
    'assets/images/banner-3.jpg',
    'assets/images/banner-4.png',
  ];
  private currentBanner = 0;

  constructor() {
    this.appointmentService.getUpcoming().subscribe((response) => {
      this.upcomingAppointments.set(response.data ?? []);
    });
  }

  nextBanner() {
    this.currentBanner = (this.currentBanner + 1) % this.banners.length;
    this.scrollToBanner();
  }

  previousBanner() {
    this.currentBanner = (this.currentBanner - 1 + this.banners.length) % this.banners.length;
    this.scrollToBanner();
  }

  private scrollToBanner() {
    if (!this.carousel) return;
    this.carousel.nativeElement.scrollTo({
      left: this.currentBanner * this.carousel.nativeElement.clientWidth,
      behavior: 'smooth',
    });
  }
}
