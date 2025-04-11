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

                if (index === -1) {
                    Swal.fire({
                        title: "¿Quieres asignar el empleado " + idEmpleado + " al proyecto " + this.selectedProject,
                        showDenyButton: true,
                        confirmButtonText: "Continuar",
                        denyButtonText: `Cancelar`
                      }).then(async (result) => {
                        if (result.isConfirmed) {
                          try {
                            const responseProjectEmployees = await axios.post('http://localhost:8080/assignEmployeeToProject?idProyecto=' + this.selectedProject+'&idEmpleado='+idEmpleado);
                            await this.getAsignacionesProyecto();      
                            Swal.fire({
                              title: 'Empleado '+ idEmpleado + " asignado al proyecto " + this.selectedProject,
                              icon: 'success',
                              draggable: true
                            })
                          } catch (error) {
                            this.dialog = false
                            Swal.fire({
                              title: error.response?.data?.error || 'Error al asignar el empleado ' + idEmpleado + " al proyecto " + this.selectedProject,
                              icon: 'error',
                              draggable: true
                            })
                          }
                        } else if (result.isDenied) {
                          Swal.fire("No se ha asignado el empleado " + idEmpleado + " al proyecto " + this.selectedProject, "", "info");
                        }
                      });
                    
                } else {
                    Swal.fire({
                        title: "¿Quieres borrar el empleado " + idEmpleado + " del proyecto " + this.selectedProject,
                        showDenyButton: true,
                        confirmButtonText: "Continuar",
                        denyButtonText: `Cancelar`
                      }).then(async (result) => {
                        if (result.isConfirmed) {
                          try {
                            const responseProjectEmployees = await axios.delete('http://localhost:8080/deleteEmployeeFromProject?idProyecto=' + this.selectedProject+'&idEmpleado='+idEmpleado);
                            await this.getAsignacionesProyecto();      
                            Swal.fire({
                              title: 'Empleado '+ idEmpleado + " borrado del proyecto " + this.selectedProject,
                              icon: 'success',
                              draggable: true
                            })
                          } catch (error) {
                            this.dialog = false
                            Swal.fire({
                              title: error.response?.data?.error || 'Error al borrar el empleado ' + idEmpleado + " del proyecto " + this.selectedProject,
                              icon: 'error',
                              draggable: true
                            })
                          }
                        } else if (result.isDenied) {
                          Swal.fire("No se ha borrado el empleado " + idEmpleado + " del proyecto " + this.selectedProject, "", "info");
                        }
                      });
                    
                }
            }
        }
        
    });

