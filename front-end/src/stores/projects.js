import { defineStore } from 'pinia'
import Swal from 'sweetalert2'

import axios from "axios"

export const useProjectsStore = () => {
    const ProjectsStore = defineStore('ProjectsStore', {
        state: () => ({ 
          dialog: false,
          editDialog: false,
          projectToEdit: {},
          projects: []
        }),
        actions: {
          async getActiveProjects () {
              await axios
                  .get('http://localhost:8080/getProjects')
                  .then(response => {
                  this.projects = response.data
              })
          },
          async getProjectById(idProyecto) {
            const response = await axios.get('http://localhost:8080/getProjectById?idProyecto='+idProyecto)
            return response.data
          },
          async deleteProject (proyecto) {
            Swal.fire({
              title: "¿Quieres borrar el proyecto?",
              showDenyButton: true,
              confirmButtonText: "Continuar",
              denyButtonText: `Cancelar`
            }).then(async (result) => {
              if (result.isConfirmed) {
                try {
                  const responseProject = await axios.delete('http://localhost:8080/deleteProject?idProyecto=' + proyecto.id);
                  await this.getActiveProjects();      
                  Swal.fire({
                    title: 'Proyecto borrado',
                    icon: 'success',
                    draggable: true
                  })
                } catch (error) {
                  this.dialog = false
                  Swal.fire({
                    title: error.response?.data?.error || 'Error al borrar proyecto',
                    icon: 'error',
                    draggable: true
                  })
                }
              } else if (result.isDenied) {
                Swal.fire("No se ha borrado el proyecto", "", "info");
              }
            });
          },
          async saveNewProject (proyecto) {
            let request = 'http://localhost:8080/createProject?desc=' + proyecto.descripcion + "&fInicio=" + proyecto.fInicio
            if (proyecto.fFinal != "") {
                request += "&fFin=" + proyecto.fFinal
            }
            if (proyecto.lugar != "") {
                request += "&lugar=" + proyecto.lugar
            }
            if (proyecto.observaciones != "") {
                request += "&obser=" + proyecto.observaciones
            }
            this.dialog = false
            Swal.fire({
              title: "¿Quieres crear el proyecto?",
              showDenyButton: true,
              confirmButtonText: "Continuar",
              denyButtonText: `Cancelar`
            }).then(async (result) => {
              if (result.isConfirmed) {
                try {
                  const response = await axios.post(request)
                  await this.getActiveProjects();
                  
          
                  Swal.fire({
                    title: 'Proyecto creado',
                    icon: 'success',
                    draggable: true
                  })
                } catch (error) {
                  
                  Swal.fire({
                    title: error.response?.data?.error || 'Error al crear proyecto',
                    icon: 'error',
                    draggable: true
                  })
                }
              } else if (result.isDenied) {
                Swal.fire("No se ha creado el proyecto", "", "info");
              }
            });
            
            
          },
          hideDialog () {
              this.dialog = false
          },
          showDialog () {
              this.dialog = true
          },
          hideEditDialog() {
            this.editDialog = false
          },
          async showEditDialog(idProyecto) {
            this.editDialog = true
            let proyecto = await this.getProjectById(idProyecto);
            this.projectToEdit = proyecto
          }, 
          async editProject() {
            this.editDialog = false
            Swal.fire({
              title: "¿Quieres editar el proyecto?",
              showDenyButton: true,
              confirmButtonText: "Continuar",
              denyButtonText: `Cancelar`
            }).then(async (result) => {
              if (result.isConfirmed) {
                try {
                  const url = "http://localhost:8080/updateProject"
                  const response = await axios.put(url, this.projectToEdit)  
                  await this.getActiveProjects();
                  Swal.fire({
                    title: 'Proyecto editado',
                    icon: 'success',
                    draggable: true
                  })               
                } catch (error) {            
                  Swal.fire({
                    title: error.response?.data?.error || 'Error al editar proyecto',
                    icon: 'error',
                    draggable: true
                  })
                }
              } else if (result.isDenied) {
                Swal.fire("No se ha editado el proyecto", "", "info");
              }
            });
          }
        }
        
    });

    const store = ProjectsStore();
    if (store.projects.length === 0) {
        store.getActiveProjects()
    }


    return store

}
