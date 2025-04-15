import { defineStore } from 'pinia'
import axios from 'axios'
import Swal from 'sweetalert2'

export const useEmployeesStore = defineStore('EmployeesStore', {
  state: () => ({
    dialog: false, //si true, se muestra formulario de alta
    editDialog: false, //si true, se muestra formulario de edicion
    employeeToEdit: {}, //diccionario con los datos del empleado a editar. 
    // Se pasa así para que cuando se abra el formulario de editar los campos estén rellenados con la info actual del empleado
    employees: [] //lista de empleados
  }),

  actions: {
    async getActiveEmployees() { // mete en la lista de empleados los empleados de la base de datos que no estén dados de baja
      const response = await axios.get('http://localhost:8080/getEmployees')
      this.employees = response.data
    },

    async getEmployeeById(idEmpleado) { // devuelve un empleado por su id
      const response = await axios.get('http://localhost:8080/getEmployeeById?idEmpleado='+idEmpleado)
      return response.data
    },

    async deleteEmployee(empleado) {
      Swal.fire({ //dialogo que confirma si se quiere borrar el empleado
        title: "¿Quieres borrar el/la empleado/a?",
        showDenyButton: true,
        confirmButtonText: "Continuar",
        denyButtonText: `Cancelar`
      }).then(async (result) => {
        if (result.isConfirmed) {
          try { //Intenta borrar el empleado, si lo consigue lanza aviso de confirmación
            const responseEmployee = await axios.delete('http://localhost:8080/deleteEmployee?idEmpleado=' + empleado.id)
            await this.getActiveEmployees();  // actualiza los empleados  
            Swal.fire({
              title: 'Empleado/a borrado/a',
              icon: 'success',
              draggable: true
            })
          } catch (error) { // si da error lo muestra
            this.dialog = false 
            Swal.fire({
              title: error.response?.data?.error || 'Error al borrar empleado/a',
              icon: 'error',
              draggable: true
            })
          }
        } else if (result.isDenied) { // si el usuario no quiere borrar el empleado muestra que el empleado no se ha borrado
          Swal.fire("No se ha borrado el/la empleado/a", "", "info");
        }
      });
    },

    async saveNewEmployee(empleado) {
      // crea la url de la petición de creación del empleado con los datos

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
      this.dialog = false // oculta el forumario de alta
      Swal.fire({ // pregunta si se quiere crear empleado 
        title: "¿Quieres crear el/la empleado/a?",
        showDenyButton: true,
        confirmButtonText: "Continuar",
        denyButtonText: `Cancelar`
      }).then(async (result) => {
        if (result.isConfirmed) {
          try { // intenta crear el empleado
            const response = await axios.post(request)
            await this.getActiveEmployees();   //actualiza la lista de empleados 
            Swal.fire({ // si se crea el empleado, lanza el aviso de que se ha creado
              title: 'Empleado/a creado/a',
              icon: 'success',
              draggable: true
            })
          } catch (error) { // si da error lo muestra
            console.log(error.response.data.error)
            
            Swal.fire({
              title: error.response?.data?.error || 'Error al crear empleado/a',
              icon: 'error',
              draggable: true
            })
          }
        } else if (result.isDenied) { // si el usuario no quiere crear el empleado avisa de que no se ha creado
          Swal.fire("No se ha creado el/la empleado/a", "", "info");
        }
      });
    },

    hideDialog() { // oculta formulario de alta
      this.dialog = false
    },

    showDialog() { //muestra formulario de alta
      this.dialog = true
    },
    hideEditDialog() { // oculta formulario de edicion
      this.editDialog = false
    },
    async showEditDialog(idEmpleado) { // muestra formulario de edición con los input rellenos con los datos actuales del empleado 
      this.editDialog = true
      let empleado = await this.getEmployeeById(idEmpleado);
      this.employeeToEdit = empleado 
    }, 
    async editEmployee() { // funcion que guarda el empleado editado
      this.editDialog = false // cierra el formualrio de edición
      Swal.fire({ // lanza dialogo que pregunta si se quiere editar el empleado
        title: "¿Quieres editar el/la empleado/a?",
        showDenyButton: true,
        confirmButtonText: "Continuar",
        denyButtonText: `Cancelar`
      }).then(async (result) => {
        if (result.isConfirmed) {
          try { // Intenta editar el empleado
            const url = "http://localhost:8080/updateEmployee"
            const response = await axios.put(url, this.employeeToEdit)
            const index = this.employees.findIndex(e => e.id === this.employeeToEdit.id);
            this.employees[index] = this.employeeToEdit
            Swal.fire({
              title: 'Empleado editado', //Si lo edita lanza aviso de que se ha editado
              icon: 'success',
              draggable: true
            }) 
          } catch (error) { // Si da error avisa del error  
            Swal.fire({
              title: error.response?.data?.error || 'Error al editar empleado/a',
              icon: 'error',
              draggable: true
            })
          }
        } else if (result.isDenied) { // Si el usuario no quiere editar el empleado, se lanza aviso de que no se ha editado
          Swal.fire("No se ha editado el/la empleado/a", "", "info");
        }
      });
    }


  }
})
