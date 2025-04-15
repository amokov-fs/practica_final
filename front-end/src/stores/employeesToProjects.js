import { defineStore } from 'pinia'
import Swal from 'sweetalert2'

import axios from "axios"

export const useEmployeesToProjectsStore = defineStore('EmployeesToProjectsStore', {
        state: () => ({
          asignaciones: {}, //diccionario de asiganciones
          selectedProject: null //variable que guarda el proyecto seleccionado en el desplegable
        }),
        actions: {
            async getAsignacionesProyecto () { // Guarda en el diccionario de asigaciones los empleados del proyecto selccionado
                const responseProjectEmployees = await axios.get('http://localhost:8080/getProjectEmployees?idProyecto=' + this.selectedProject);
                let employeesProject = []
                for (const employee in responseProjectEmployees.data) {
                    employeesProject.push(responseProjectEmployees.data[employee].id)
                }
                this.asignaciones[this.selectedProject] = employeesProject
            },
            estaAsignado (idEmpleado) { // Comprueba si el empleado está asigando a un proyecto para marcar el checkbox
                const asignados = this.asignaciones[this.selectedProject] || [];
                return asignados.includes(idEmpleado);
            },
            async modificarAsignacion (idEmpleado) { //asigna o desasigna el empleado al proyecto selccionado
                if (!Array.isArray(this.asignaciones[this.selectedProject])) {
                    this.asignaciones[this.selectedProject] = [];
                }
                const index = this.asignaciones[this.selectedProject].indexOf(idEmpleado);

                // Info empleado a asignar/desasignar para los dialogos de confirmación

                const responseEmpleado = await axios.get('http://localhost:8080/getEmployeeById?idEmpleado='+idEmpleado);

                const empleado = responseEmpleado.data

                const nombreCompletoEmpleado = empleado.nombre + " " + empleado.apellido1 + " " + empleado.apellido2

                // Info proyecto seleccionado para los dialogos de confirmación

                const response = await axios.get('http://localhost:8080/getProjectById?idProyecto='+this.selectedProject)
                
                const proyecto = response.data

                const descripcionProyecto = proyecto.descripcion

                if (index === -1) { // Si el empleado no está asignado pregunta si lo quiere asignar
                    Swal.fire({
                        title: "¿Quieres asignar el empleado " + nombreCompletoEmpleado + " al proyecto " + descripcionProyecto,
                        showDenyButton: true,
                        confirmButtonText: "Continuar",
                        denyButtonText: `Cancelar`
                      }).then(async (result) => {
                        if (result.isConfirmed) {
                          try { // intenta asignarlo, si lo consigue avisa que se ha asignado
                            const responseProjectEmployees = await axios.post('http://localhost:8080/assignEmployeeToProject?idProyecto=' + this.selectedProject+'&idEmpleado='+idEmpleado);
                            await this.getAsignacionesProyecto();  // acutaliza las asignaciones    
                            Swal.fire({
                              title: 'Empleado '+ nombreCompletoEmpleado + " asignado al proyecto " + descripcionProyecto,
                              icon: 'success',
                              draggable: true
                            })
                          } catch (error) { // en caso de error, muestra aviso del error
                            this.dialog = false
                            Swal.fire({
                              title: error.response?.data?.error || 'Error al asignar el empleado ' + nombreCompletoEmpleado + " al proyecto " + descripcionProyecto,
                              icon: 'error',
                              draggable: true
                            })
                          }
                        } else if (result.isDenied) { // Si el usuario no quiere asignar el empleado, avisa que no se ha asignado
                          Swal.fire("No se ha asignado el empleado " + nombreCompletoEmpleado + " al proyecto " + descripcionProyecto, "", "info");
                        }
                      });
                    
                } else { // Si el empleado está asignado pregunta si lo quiere borrar
                    Swal.fire({
                        title: "¿Quieres borrar el empleado " + nombreCompletoEmpleado + " del proyecto " + descripcionProyecto,
                        showDenyButton: true,
                        confirmButtonText: "Continuar",
                        denyButtonText: `Cancelar`
                      }).then(async (result) => {
                        if (result.isConfirmed) { 
                          try { // intenta borrarlo, si lo consigue avisa de que se ha borrado el empleado del proyecto seleccionado
                            const responseProjectEmployees = await axios.delete('http://localhost:8080/deleteEmployeeFromProject?idProyecto=' + this.selectedProject+'&idEmpleado='+idEmpleado);
                            await this.getAsignacionesProyecto();      
                            Swal.fire({
                              title: 'Empleado '+ nombreCompletoEmpleado + " borrado del proyecto " + descripcionProyecto,
                              icon: 'success',
                              draggable: true
                            })
                          } catch (error) { // Si no lo consigue muestra el error
                            this.dialog = false
                            Swal.fire({
                              title: error.response?.data?.error || 'Error al borrar el empleado ' + nombreCompletoEmpleado + " del proyecto " + descripcionProyecto,
                              icon: 'error',
                              draggable: true
                            })
                          }
                        } else if (result.isDenied) { // Si el usuario no quiere borrar el empleado se avisa de que no se ha borrado el empleado del proyecto
                          Swal.fire("No se ha borrado el empleado " + nombreCompletoEmpleado + " del proyecto " + descripcionProyecto, "", "info");
                        }
                      });
                    
                }
            }
        }
        
    });

