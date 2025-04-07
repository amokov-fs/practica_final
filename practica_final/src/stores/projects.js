import { ref, computed } from 'vue'
import { defineStore } from 'pinia'

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
