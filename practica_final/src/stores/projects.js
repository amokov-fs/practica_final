import { defineStore } from 'pinia'
import Swal from 'sweetalert2'

import axios from "axios"

export const useProjectsStore = () => {
    const ProjectsStore = defineStore('ProjectsStore', {
        state: () => ({ 
          dialog: false,
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
          async deleteProject (proyecto) {
            const index = this.projects.indexOf(proyecto);
            this.projects.splice(index, 1);
            const responseProject = await axios.delete('http://localhost:8080/deleteProject?idProyecto=' + proyecto.id);
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
            
            try {
                const response = await axios.post(request)
                await this.getActiveProjects();
                this.dialog = false
        
                Swal.fire({
                  title: 'Proyecto creado',
                  icon: 'success',
                  draggable: true
                })
              } catch (error) {
                this.dialog = false
                Swal.fire({
                  title: error.response?.data?.error || 'Error al crear proyecto',
                  icon: 'error',
                  draggable: true
                })
              }
          },
          hideDialog () {
              this.dialog = false
          },
          showDialog () {
              this.dialog = true
          }
        }
        
    });

    const store = ProjectsStore();
    if (store.projects.length === 0) {
        store.getActiveProjects()
    }


    return store

}
