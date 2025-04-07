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
                hint="example of helper text only on focus"
                label="Fecha de inicio*"
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
                hint="example of persistent helper text"
                label="Fecha de finalizacion"
                persistent-hint
              ></v-text-field>
            </v-col>
            
            <v-col
              cols="12"
              md="6"
              sm="6"
            >
              <v-text-field
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
            @click.stop="dialog = false"
          ></v-btn>
        </v-card-actions>
    </v-card>
  </v-dialog>
</template>


<script setup>
import { shallowRef } from 'vue'
import {useProjectsStore} from '../stores/projects'

const projectsStore = useProjectsStore();

</script>

<style>

</style>