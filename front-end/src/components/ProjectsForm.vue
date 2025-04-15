<template>
  <v-dialog v-model="projectsStore.dialog" max-width="700px">
    <v-card>
      <v-card-text> <!-- muestra el formulario si dialog de la store de proyectos es true --> 
          <v-row dense>
            <v-col
              cols="12"
              md="6"
              sm="6"
            >
              <v-text-field
                v-model = "descripcion"
                label="Descripcion Proyecto*"
                required
              ></v-text-field><!-- Input Descripcion --> 
            </v-col>
          
            <v-col
              cols="12"
              md="6"
              sm="6"
            >
              <v-text-field
                v-model = "fInicio"
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
                v-model = "fFinal"
                label="Fecha de finalizacion (yyyy-mm-dd)"
                persistent-hint
              ></v-text-field> <!-- Input fecha final --> 
            </v-col>
            
            <v-col
              cols="12"
              md="6"
              sm="6"
            >
              <v-text-field
                v-model = "lugar"
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
                v-model = "observaciones"
                label="Observaciones"
              ></v-text-field> <!-- Input observaciones --> 
            </v-col>
          </v-row>

          <small class="text-caption text-medium-emphasis">*campo requerido</small>
        </v-card-text>
        <v-card-actions>
          <v-spacer></v-spacer>
          <!-- boton cancelar sin guardar el formulario --> 
          <v-btn text="Cancelar" variant="plain" @click.stop="projectsStore.hideDialog()"></v-btn>
          <!-- boton de guardar el proyecto --> 
          <v-btn
            color="primary"
            text="Guardar"
            variant="tonal"
            @click ="projectsStore.saveNewProject({
              'descripcion':descripcion,
              'fInicio':fInicio,
              'fFinal':fFinal,
              'lugar':lugar,
              'observaciones':observaciones
            })"
          ></v-btn>
        </v-card-actions>
    </v-card>
  </v-dialog>
</template>


<script setup>

//importar store de proyecto
import {ref} from 'vue'
import {useProjectsStore} from '../stores/projects'

// variables que se actualizan con los inputs

const descripcion = ref("")
const fInicio = ref("")
const fFinal = ref("")
const lugar = ref("")
const observaciones = ref("")

// diccionario del proyecto nuevo

const nuevoProyecto = {
  'descripcion':descripcion.value,
  'fInicio':fInicio.value,
  'fFinal':fFinal.value,
  'lugar':lugar.value,
  'observaciones':observaciones.value
}



const projectsStore = useProjectsStore();

</script>

<style>

</style>