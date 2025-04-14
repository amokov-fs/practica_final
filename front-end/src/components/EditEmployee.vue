<template>
  <v-dialog v-model="employeesStore.editDialog" max-width="700px">
    <v-card>
        <v-card-text>
            <v-row dense>
                <v-col md="6">
                    <v-text-field
                    v-model = "employeesStore.employeeToEdit.nif"
                    label="NIF*"
                    required
                    ></v-text-field>
                </v-col>
                
                <v-col md="6">
                    <v-text-field
                    v-model = "employeesStore.employeeToEdit.nombre"
                    label="Nombre*"
                    required
                    ></v-text-field>
                </v-col>
            </v-row>
            <v-row dense>
                <v-col md="6">
                    <v-text-field
                    v-model = "employeesStore.employeeToEdit.apellido1"
                    label="Apellido 1*"
                    required
                    ></v-text-field>
                </v-col>
                <v-col md="6">
                    <v-text-field
                    v-model = "employeesStore.employeeToEdit.apellido2"
                    label="Apellido 2*"
                    required
                    ></v-text-field>
                </v-col>
            </v-row>
            <v-row dense>
                <v-col md="6">
                    <v-text-field
                    v-model = "employeesStore.employeeToEdit.fechaNacimiento"
                    label="Fecha de nacimiento* (yyyy-mm-dd)"
                    required
                    ></v-text-field>
                </v-col>
                <v-col md="6">
                    <v-text-field
                    v-model = "employeesStore.employeeToEdit.fechaAlta"
                    label="Fecha de Alta* (yyyy-mm-dd)"
                    required
                    ></v-text-field>
                </v-col>
            </v-row>
            <v-row dense>
                <v-col md="6">
                    <v-text-field
                    v-model = "employeesStore.employeeToEdit.ntelefono1"
                    label="Telefono 1*"
                    required
                    ></v-text-field>
                </v-col>
                <v-col md="6">
                    <v-text-field
                    v-model = "employeesStore.employeeToEdit.ntelefono2"
                    label="Telefono 2"
                    ></v-text-field>
                </v-col>
            </v-row>
            <v-row dense>
                <v-col md="12">
                    <v-text-field
                    v-model = "employeesStore.employeeToEdit.email"
                    label="Email*"
                    required
                    ></v-text-field>
                </v-col>
            </v-row>
            <v-row dense>
                <v-col md="6">
                    <v-text-field
                    v-model = "employeesStore.employeeToEdit.ecivil"
                    label="Estado Civil* (S - Soltero/a; C- Casado/a)"
                    required
                    ></v-text-field>
                </v-col>
                <v-col md="6">
                    <v-text-field
                    v-model = "employeesStore.employeeToEdit.formacionU"
                    label="Formacion Universitaria* (S-Si; N-No)"
                    required
                    ></v-text-field>
                </v-col>
            </v-row>

            <small class="text-caption text-medium-emphasis">*campo requerido</small>
            </v-card-text>
        <v-card-actions>
            <v-spacer></v-spacer>

            <v-btn text="Cancelar" variant="plain" @click.stop="employeesStore.hideEditDialog()"></v-btn>

            <v-btn
                color="primary"
                text="Editar"
                variant="tonal"
                @click ="employeesStore.editEmployee()"
            ></v-btn>
        </v-card-actions>
    </v-card>
  </v-dialog>
</template>


<script setup>

import {ref, onMounted} from 'vue'
import {useEmployeesStore} from '../stores/employees'

const employeesStore = useEmployeesStore();

/*
const props = defineProps({
  idEmployee: Number // a cada componente de edtar se le pasa el id
})*/

</script>

<style>

</style>