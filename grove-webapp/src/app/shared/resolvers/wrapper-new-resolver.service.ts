import {Injectable} from '@angular/core';
import { ActivatedRouteSnapshot, RouterStateSnapshot } from '@angular/router';
import {CREATION, WrapperService} from '../services/wrapper.service';
import {Observable} from 'rxjs';

@Injectable({providedIn: 'root'})
export class WrapperNewResolver  {

  constructor(private wrapperService: WrapperService) {
  }

  resolve(route: ActivatedRouteSnapshot, state: RouterStateSnapshot): Observable<void> | Promise<void> | void {
    this.wrapperService.entity = 'Jeux';
    this.wrapperService.mode = CREATION;
  }
}
