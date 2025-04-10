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
                    this.asignaciones[this.selectedProject].push(idEmpleado);
                    const responseProjectEmployees = await axios.post('http://localhost:8080/assignEmployeeToProject?idProyecto=' + this.selectedProject+'&idEmpleado='+idEmpleado);
                } else {
                    this.asignaciones[this.selectedProject].splice(index, 1);
                    const responseProjectEmployees = await axios.delete('http://localhost:8080/deleteEmployeeFromProject?idProyecto=' + this.selectedProject+'&idEmpleado='+idEmpleado);
                }
            }
        }
        
    });

