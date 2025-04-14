import { defineStore } from 'pinia'
import Swal from 'sweetalert2'

import axios from "axios"

export const useEmployeesToProjectsStore = defineStore('EmployeesToProjectsStore', {
        state: () => ({
          asignaciones: {},
          selectedProject: null
        }),
        actions: {
            async getAsignacionesProyecto () {
                const responseProjectEmployees = await axios.get('http://localhost:8080/getProjectEmployees?idProyecto=' + this.selectedProject);
                let employeesProject = []
                for (const employee in responseProjectEmployees.data) {
                    employeesProject.push(responseProjectEmployees.data[employee].id)
                }
                this.asignaciones[this.selectedProject] = employeesProject
            },
            estaAsignado (idEmpleado) {
                const asignados = this.asignaciones[this.selectedProject] || [];
                return asignados.includes(idEmpleado);
            },
            async modificarAsignacion (idEmpleado) {
                if (!Array.isArray(this.asignaciones[this.selectedProject])) {
                    this.asignaciones[this.selectedProject] = [];
                }
                const index = this.asignaciones[this.selectedProject].indexOf(idEmpleado);

                const responseEmpleado = await axios.get('http://localhost:8080/getEmployeeById?idEmpleado='+idEmpleado);

                const empleado = responseEmpleado.data

                const nombreCompletoEmpleado = empleado.nombre + " " + empleado.apellido1 + " " + empleado.apellido2

                const response = await axios.get('http://localhost:8080/getProjectById?idProyecto='+this.selectedProject)
                
                const proyecto = response.data

                const descripcionProyecto = proyecto.descripcion

                if (index === -1) {
                    Swal.fire({
                        title: "¿Quieres asignar el empleado " + nombreCompletoEmpleado + " al proyecto " + descripcionProyecto,
                        showDenyButton: true,
                        confirmButtonText: "Continuar",
                        denyButtonText: `Cancelar`
                      }).then(async (result) => {
                        if (result.isConfirmed) {
                          try {
                            const responseProjectEmployees = await axios.post('http://localhost:8080/assignEmployeeToProject?idProyecto=' + this.selectedProject+'&idEmpleado='+idEmpleado);
                            await this.getAsignacionesProyecto();      
                            Swal.fire({
                              title: 'Empleado '+ nombreCompletoEmpleado + " asignado al proyecto " + descripcionProyecto,
                              icon: 'success',
                              draggable: true
                            })
                          } catch (error) {
                            this.dialog = false
                            Swal.fire({
                              title: error.response?.data?.error || 'Error al asignar el empleado ' + nombreCompletoEmpleado + " al proyecto " + descripcionProyecto,
                              icon: 'error',
                              draggable: true
                            })
                          }
                        } else if (result.isDenied) {
                          Swal.fire("No se ha asignado el empleado " + nombreCompletoEmpleado + " al proyecto " + descripcionProyecto, "", "info");
                        }
                      });
                    
                } else {
                    Swal.fire({
                        title: "¿Quieres borrar el empleado " + nombreCompletoEmpleado + " del proyecto " + descripcionProyecto,
                        showDenyButton: true,
                        confirmButtonText: "Continuar",
                        denyButtonText: `Cancelar`
                      }).then(async (result) => {
                        if (result.isConfirmed) {
                          try {
                            const responseProjectEmployees = await axios.delete('http://localhost:8080/deleteEmployeeFromProject?idProyecto=' + this.selectedProject+'&idEmpleado='+idEmpleado);
                            await this.getAsignacionesProyecto();      
                            Swal.fire({
                              title: 'Empleado '+ nombreCompletoEmpleado + " borrado del proyecto " + descripcionProyecto,
                              icon: 'success',
                              draggable: true
                            })
                          } catch (error) {
                            this.dialog = false
                            Swal.fire({
                              title: error.response?.data?.error || 'Error al borrar el empleado ' + nombreCompletoEmpleado + " del proyecto " + descripcionProyecto,
                              icon: 'error',
                              draggable: true
                            })
                          }
                        } else if (result.isDenied) {
                          Swal.fire("No se ha borrado el empleado " + nombreCompletoEmpleado + " del proyecto " + descripcionProyecto, "", "info");
                        }
                      });
                    
                }
            }
        }
        
    });

