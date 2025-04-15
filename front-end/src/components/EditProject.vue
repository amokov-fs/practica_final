<template>
  <v-dialog v-model="projectsStore.editDialog" max-width="700px">
    <v-card> <!-- muestra el formulario si editDialog de la store de proyectos es true --> 
      <v-card-text>
          <v-row dense>
            <v-col
              cols="12"
              md="6"
              sm="6"
            >
              <v-text-field
                v-model = "projectsStore.projectToEdit.descripcion"
                label="Descripcion Proyecto*"
                required
              ></v-text-field> <!-- Input Descripcion --> 
            </v-col>
          
            <v-col
              cols="12"
              md="6"
              sm="6"
            >
              <v-text-field
                v-model = "projectsStore.projectToEdit.fechaInicio"
                label="Fecha de inicio* (yyyy-mm-dd)"
              ></v-text-field> <!-- Input fecha inicio --> 
            </v-col>
            </v-row>
            <v-row dense>
            <v-col
              cols="12"
              md="6"
              sm="6"
            >
              <v-text-field
                v-model = "projectsStore.projectToEdit.fechaFin"
                label="Fecha de finalizacion (yyyy-mm-dd)"
                persistent-hint
              ></v-text-field> <!-- Input fecha fin --> 
            </v-col>
            
            <v-col
              cols="12"
              md="6"
              sm="6"
            >
              <v-text-field
                v-model = "projectsStore.projectToEdit.lugar"
                label="Lugar"
              ></v-text-field> <!-- Input lugar --> 
            </v-col>
            </v-row>
            <v-row dense>
            <v-col
              cols="12"
              md="12"
              sm="6"
            >
              <v-text-field
                v-model = "projectsStore.projectToEdit.observaciones"
                label="Observaciones"
              ></v-text-field> <!-- Input observaciones --> 
            </v-col>
          </v-row>

          <small class="text-caption text-medium-emphasis">*campo requerido</small>
        </v-card-text>
        <v-card-actions>
          <v-spacer></v-spacer>
          <!-- Boton para cerrar el formulario de edición sin guardar --> 
          <v-btn text="Cancelar" variant="plain" @click.stop="projectsStore.hideEditDialog()"></v-btn>
          <!-- Botón para guardar el proyecto editado --> 
          <v-btn
            color="primary"
            text="Editar"
            variant="tonal"
            @click ="projectsStore.editProject()"
          ></v-btn>
        </v-card-actions>
    </v-card>
  </v-dialog>
</template>


<script setup>
//Importar la store de proyectos

import {useProjectsStore} from '../stores/projects'

const projectsStore = useProjectsStore();

</script>

<style>

</style>