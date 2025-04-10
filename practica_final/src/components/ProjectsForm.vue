<template>
  <v-dialog v-model="projectsStore.dialog" max-width="700px">
    <v-card>
      <v-card-text>
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
              ></v-text-field>
            </v-col>
          
            <v-col
              cols="12"
              md="6"
              sm="6"
            >
              <v-text-field
                v-model = "fInicio"
                label="Fecha de inicio* (yyyy-mm-dd)"
              ></v-text-field>
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
              ></v-text-field>
            </v-col>
            
            <v-col
              cols="12"
              md="6"
              sm="6"
            >
              <v-text-field
                v-model = "lugar"
                label="Lugar"
              ></v-text-field>
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
              ></v-text-field>
            </v-col>
          </v-row>

          <small class="text-caption text-medium-emphasis">*campo requerido</small>
        </v-card-text>
        <v-card-actions>
          <v-spacer></v-spacer>

          <v-btn text="Close" variant="plain" @click.stop="projectsStore.hideDialog()"></v-btn>

          <v-btn
            color="primary"
            text="Save"
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
import {ref} from 'vue'
import {useProjectsStore} from '../stores/projects'

const descripcion = ref("")
const fInicio = ref("")
const fFinal = ref("")
const lugar = ref("")
const observaciones = ref("")

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