import { defineStore } from 'pinia'
import axios from 'axios'
import Swal from 'sweetalert2'

export const useEmployeesStore = defineStore('EmployeesStore', {
  state: () => ({
    dialog: false,
    editDialog: false,
    employeeToEdit: {},
    employees: []
  }),

  actions: {
    async getActiveEmployees() {
      const response = await axios.get('http://localhost:8080/getEmployees')
      this.employees = response.data
    },

    async getEmployeeById(idEmpleado) {
      const response = await axios.get('http://localhost:8080/getEmployeeById?idEmpleado='+idEmpleado)
      return response.data
    },

    async deleteEmployee(empleado) {
      Swal.fire({
        title: "¿Quieres borrar el/la empleado/a?",
        showDenyButton: true,
        confirmButtonText: "Continuar",
        denyButtonText: `Cancelar`
      }).then(async (result) => {
        if (result.isConfirmed) {
          try {
            const responseEmployee = await axios.delete('http://localhost:8080/deleteEmployee?idEmpleado=' + empleado.id)
            await this.getActiveEmployees();      
            Swal.fire({
              title: 'Empleado/a borrado/a',
              icon: 'success',
              draggable: true
            })
          } catch (error) {
            this.dialog = false
            Swal.fire({
              title: error.response?.data?.error || 'Error al borrar empleado/a',
              icon: 'error',
              draggable: true
            })
          }
        } else if (result.isDenied) {
          Swal.fire("No se ha borrado el/la empleado/a", "", "info");
        }
      });
    },

    async saveNewEmployee(empleado) {
      let request = 'http://localhost:8080/createEmployee?nifEmpleado=' + empleado.nif

      request += "&nombreEmpleado=" + empleado.nombre
      request += "&ap1Empleado=" + empleado.apellido1
      request += "&ap2Empleado=" + empleado.apellido2
      request += "&fNacEmpleado=" + empleado.fechaNacimiento
      request += "&tlf1Empleado=" + empleado.telefono1
      if (empleado.telefono2 !== "") {
        request += "&tlf2Empleado=" + empleado.telefono2
      }
      request += "&emailEmpleado=" + empleado.email
      request += "&fAltaEmpleado=" + empleado.fechaAlta
      request += "&edoEmpleado=" + empleado.ecivil
      request += "&uniEmpleado=" + empleado.formacionU
      this.dialog = false
      Swal.fire({
        title: "¿Quieres crear el/la empleado/a?",
        showDenyButton: true,
        confirmButtonText: "Continuar",
        denyButtonText: `Cancelar`
      }).then(async (result) => {
        if (result.isConfirmed) {
          try {
            const response = await axios.post(request)
            await this.getActiveEmployees();    
            Swal.fire({
              title: 'Empleado/a creado/a',
              icon: 'success',
              draggable: true
            })
          } catch (error) {
            console.log(error.response.data.error)
            
            Swal.fire({
              title: error.response?.data?.error || 'Error al crear empleado/a',
              icon: 'error',
              draggable: true
            })
          }
        } else if (result.isDenied) {
          Swal.fire("No se ha creado el/la empleado/a", "", "info");
        }
      });
    },

    hideDialog() {
      this.dialog = false
    },

    showDialog() {
      this.dialog = true
    },
    hideEditDialog() {
      this.editDialog = false
    },
    async showEditDialog(idEmpleado) {
      this.editDialog = true
      let empleado = await this.getEmployeeById(idEmpleado);
      this.employeeToEdit = empleado
    }, 
    async editEmployee() {
      this.editDialog = false
      Swal.fire({
        title: "¿Quieres editar el/la empleado/a?",
        showDenyButton: true,
        confirmButtonText: "Continuar",
        denyButtonText: `Cancelar`
      }).then(async (result) => {
        if (result.isConfirmed) {
          try {
            const url = "http://localhost:8080/updateEmployee"
            const response = await axios.put(url, this.employeeToEdit)
            const index = this.employees.findIndex(e => e.id === this.employeeToEdit.id);
            this.employees[index] = this.employeeToEdit
            Swal.fire({
              title: 'Empleado editado',
              icon: 'success',
              draggable: true
            }) 
          } catch (error) {            
            Swal.fire({
              title: error.response?.data?.error || 'Error al editar empleado/a',
              icon: 'error',
              draggable: true
            })
          }
        } else if (result.isDenied) {
          Swal.fire("No se ha editado el/la empleado/a", "", "info");
        }
      });
    }


  }
})
