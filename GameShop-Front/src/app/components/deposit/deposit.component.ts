import { Component, ElementRef, ViewChild } from '@angular/core';
import { ActivatedRoute, Router } from '@angular/router';
import { AuthService } from 'src/app/services/auth/auth.service';
import Swal from 'sweetalert2';

@Component({
  selector: 'app-deposit',
  templateUrl: './deposit.component.html',
  styleUrls: ['./deposit.component.css']
})
export class DepositComponent {
  selectedOption: string = '10€';
  customValue: number = 0;
  password: string = ''

  constructor(private authService:AuthService,private route:Router){

  }

  ingresarCantidad() {
    if(this.password !=''){
      this.authService.checkPassword(this.password).subscribe(
        
      data=>{
        if(data==true){
          const cantidad = this.selectedOption === 'Personalizado' ? this.customValue : parseInt(this.selectedOption, 10);
          if(cantidad<0 || cantidad >998){
            Swal.fire('Introduce una cantidad valida')
          }else{
            this.authService.addMoney(cantidad).subscribe(()=>{
              Swal.fire("Fondos ingresados correctamente")
              this.route.navigateByUrl('#')})
          } 
        }else {
          Swal.fire('Contraseña incorrecta')
        }   
      })
    }else{
      Swal.fire('Debes introducir la contraseña para continuar')
    }
    
  }
}
