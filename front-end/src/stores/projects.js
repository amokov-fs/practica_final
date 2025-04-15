import { defineStore } from 'pinia'
import Swal from 'sweetalert2'

import axios from "axios"

export const useProjectsStore = () => {
    const ProjectsStore = defineStore('ProjectsStore', {
        state: () => ({ 
          dialog: false, //si true, se muestra formulario de alta
          editDialog: false, //si true, se muestra formulario de edicion
          projectToEdit: {}, //diccionario con los datos del proyecto a editar.
          // Se pasa así para que cuando se abra el formulario de editar los campos estén rellenados con la info actual del empleado 
          projects: [] //lista de proyectos
        }),
        actions: {
          async getActiveProjects () {
              await axios // mete en la lista de proyectos los proyectos de la base de datos que no estén dados de baja
                  .get('http://localhost:8080/getProjects')
                  .then(response => {
                  this.projects = response.data
              })
          },
          async getProjectById(idProyecto) { // devuelve un proyecto por su id
            const response = await axios.get('http://localhost:8080/getProjectById?idProyecto='+idProyecto)
            return response.data
          },
          async deleteProject (proyecto) {
            Swal.fire({ //dialogo que confirma si se quiere borrar el proyecto
              title: "¿Quieres borrar el proyecto?",
              showDenyButton: true,
              confirmButtonText: "Continuar",
              denyButtonText: `Cancelar`
            }).then(async (result) => {
              if (result.isConfirmed) {
                try { //Intenta borrar el proyecto, si lo consigue lanza aviso de confirmación
                  const responseProject = await axios.delete('http://localhost:8080/deleteProject?idProyecto=' + proyecto.id);
                  await this.getActiveProjects();      
                  Swal.fire({
                    title: 'Proyecto borrado',
                    icon: 'success',
                    draggable: true
                  })
                } catch (error) { // si da error lo muestra
                  this.dialog = false
                  Swal.fire({
                    title: error.response?.data?.error || 'Error al borrar proyecto',
                    icon: 'error',
                    draggable: true
                  })
                }
              } else if (result.isDenied) { // si el usuario no quiere borrar el proyecto muestra que el proyecto no se ha borrado
                Swal.fire("No se ha borrado el proyecto", "", "info");
              }
            });
          },
          async saveNewProject (proyecto) {
            // crea la url de la petición de creación del proyecto con los datos
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
            this.dialog = false // oculta el forumario de alta
            Swal.fire({ // pregunta si se quiere crear proyecto 
              title: "¿Quieres crear el proyecto?",
              showDenyButton: true,
              confirmButtonText: "Continuar",
              denyButtonText: `Cancelar`
            }).then(async (result) => {
              if (result.isConfirmed) {
                try { // intenta crear el proyecto
                  const response = await axios.post(request)
                  await this.getActiveProjects(); //actualiza la lista de proyecto 
                  
          
                  Swal.fire({ // si se crea el proyecto, lanza el aviso de que se ha creado 
                    title: 'Proyecto creado',
                    icon: 'success',
                    draggable: true
                  })
                } catch (error) { // si da error lo muestra
                  
                  Swal.fire({
                    title: error.response?.data?.error || 'Error al crear proyecto',
                    icon: 'error',
                    draggable: true
                  })
                }
              } else if (result.isDenied) { // si el usuario no quiere crear el proyecto avisa de que no se ha creado
                Swal.fire("No se ha creado el proyecto", "", "info");
              }
            });
            
            
          },
          hideDialog () { // oculta formulario de alta
              this.dialog = false
          },
          showDialog () { // muestra formulario de alta
              this.dialog = true
          },
          hideEditDialog() { // oculta formulario de edicion
            this.editDialog = false
          },
          async showEditDialog(idProyecto) { // muestra formulario de edición con los input rellenos con los datos actuales del proyecto
            this.editDialog = true
            let proyecto = await this.getProjectById(idProyecto);
            this.projectToEdit = proyecto
          }, 
          async editProject() { // funcion que guarda el proyecto editado
            this.editDialog = false // cierra el formualrio de edición
            Swal.fire({ // lanza dialogo que pregunta si se quiere editar el proyecto
              title: "¿Quieres editar el proyecto?",
              showDenyButton: true,
              confirmButtonText: "Continuar",
              denyButtonText: `Cancelar`
            }).then(async (result) => {
              if (result.isConfirmed) {
                try { // Intenta editar el proyecto
                  const url = "http://localhost:8080/updateProject"
                  const response = await axios.put(url, this.projectToEdit)  
                  await this.getActiveProjects();
                  Swal.fire({
                    title: 'Proyecto editado', //Si lo edita lanza aviso de que se ha editado
                    icon: 'success',
                    draggable: true
                  })               
                } catch (error) { // Si da error avisa del error    
                  Swal.fire({
                    title: error.response?.data?.error || 'Error al editar proyecto',
                    icon: 'error',
                    draggable: true
                  })
                }
              } else if (result.isDenied) { // Si el usuario no quiere editar el proyecto, se lanza aviso de que no se ha editado
                Swal.fire("No se ha editado el proyecto", "", "info");
              }
            });
          }
        }
        
    });

    const store = ProjectsStore();
    if (store.projects.length === 0) {
        store.getActiveProjects() // a la hora de crear la store, se crea la lista de empleados con aquellos que no estén dados de baja
    }


    return store

}
