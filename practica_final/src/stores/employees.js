import { ref, computed } from 'vue'
import { defineStore } from 'pinia'

import axios from "axios"

import Swal from 'sweetalert2';

export const useEmployeesStore = () => {
    const EmployeesStore = defineStore('EmployeesStore', {
        state: () => ({ 
          dialog: false,
          employees: []
        }),
        actions: {
          async getActiveEmployees () {
              await axios
                  .get('http://localhost:8080/getEmployees')
                  .then(response => {
                  this.employees = response.data
              })
          },
          async deleteEmployee (empleado) {
            const index = this.employees.indexOf(empleado);
            this.employees.splice(index, 1);
            const responseProject = await axios.delete('http://localhost:8080/deleteEmployee?idEmpleado=' + empleado.id);
          },
          async saveNewEmployee (empleado) {
            
            let request = 'http://localhost:8080/createEmployee?nifEmpleado=' + empleado.nif
            
            request += "&nombreEmpleado=" + empleado.nombre
            request += "&ap1Empleado=" + empleado.apellido1
            request += "&ap2Empleado=" + empleado.apellido2
            request += "&fNacEmpleado=" + empleado.fechaNacimiento
            request += "&tlf1Empleado=" + empleado.telefono1
            request += "&tlf2Empleado=" + empleado.telefono2
            request += "&emailEmpleado=" + empleado.email
            request += "&fAltaEmpleado=" + empleado.fechaAlta
            request += "&edoEmpleado=" + empleado.ecivil
            request += "&uniEmpleado=" + empleado.formacionU

            try {
              const responseEmployee = await axios.post(request);
              this.employees.push(responseEmployee.data);
              this.dialog = false;
              
            } catch (error) {
              this.dialog = false;
              Swal.fire({
                title: error.response.data.error,
                icon: "error",
                draggable: true
              });
            }    
            Swal.fire({
              title: "Usuario creado",
              icon: "success",
              draggable: true
            });        
          },
          hideDialog () {
              this.dialog = false
          },
          showDialog () {
              this.dialog = true
          }
        }
        
    });

    const store = EmployeesStore();
    if (store.employees.length === 0) {
        store.getActiveEmployees()
    }


    return store

}
