<template>
  <v-dialog v-model="employeesStore.editDialog" max-width="700px"> 
    <v-card>
        <v-card-text> <!-- muestra el formulario si editDialog de la store de empleados es true --> 
            <v-row dense>
                <v-col md="6">
                    <v-text-field 
                    v-model = "employeesStore.employeeToEdit.nif"
                    label="NIF*"
                    required
                    ></v-text-field> <!-- Input NIF --> 
                </v-col>
                
                <v-col md="6">
                    <v-text-field
                    v-model = "employeesStore.employeeToEdit.nombre"
                    label="Nombre*"
                    required
                    ></v-text-field> <!-- Input Nombre --> 
                </v-col>
            </v-row>
            <v-row dense>
                <v-col md="6">
                    <v-text-field
                    v-model = "employeesStore.employeeToEdit.apellido1"
                    label="Apellido 1*"
                    required
                    ></v-text-field> <!-- Input Apellido1 --> 
                </v-col>
                <v-col md="6">
                    <v-text-field
                    v-model = "employeesStore.employeeToEdit.apellido2"
                    label="Apellido 2*" 
                    required
                    ></v-text-field> <!-- Input Apellido2 --> 
                </v-col>
            </v-row>
            <v-row dense>
                <v-col md="6">
                    <v-text-field
                    v-model = "employeesStore.employeeToEdit.fechaNacimiento"
                    label="Fecha de nacimiento* (yyyy-mm-dd)"
                    required
                    ></v-text-field> <!-- Input Fecha de nacimiento --> 
                </v-col>
                <v-col md="6">
                    <v-text-field
                    v-model = "employeesStore.employeeToEdit.fechaAlta"
                    label="Fecha de Alta* (yyyy-mm-dd)"
                    required
                    ></v-text-field> <!-- Input Fecha de alta --> 
                </v-col>
            </v-row>
            <v-row dense>
                <v-col md="6">
                    <v-text-field
                    v-model = "employeesStore.employeeToEdit.ntelefono1"
                    label="Telefono 1*"
                    required
                    ></v-text-field>  <!-- Input Telefono 1 --> 
                </v-col>
                <v-col md="6">
                    <v-text-field
                    v-model = "employeesStore.employeeToEdit.ntelefono2"
                    label="Telefono 2"
                    ></v-text-field> <!-- Input Telefono 2 --> 
                </v-col>
            </v-row>
            <v-row dense>
                <v-col md="12">
                    <v-text-field
                    v-model = "employeesStore.employeeToEdit.email"
                    label="Email*"
                    required
                    ></v-text-field> <!-- Input Email --> 
                </v-col>
            </v-row>
            <v-row dense>
                <v-col md="6">
                    <v-text-field
                    v-model = "employeesStore.employeeToEdit.ecivil"
                    label="Estado Civil* (S - Soltero/a; C- Casado/a)"
                    required
                    ></v-text-field> <!-- Input Estado civil --> 
                </v-col>
                <v-col md="6">
                    <v-text-field
                    v-model = "employeesStore.employeeToEdit.formacionU"
                    label="Formacion Universitaria* (S-Si; N-No)"
                    required
                    ></v-text-field> <!-- Input Formacion universitaria --> 
                </v-col>
            </v-row>

            <small class="text-caption text-medium-emphasis">*campo requerido</small>
            </v-card-text>
        <v-card-actions>
            <v-spacer></v-spacer>
            <!-- boton que cierra el formulario de ediición sin guardar -->
            <v-btn text="Cancelar" variant="plain" @click.stop="employeesStore.hideEditDialog()"></v-btn> 
             <!-- boton que guarda el empleado editado -->
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
//Importar la store de empleados
import {useEmployeesStore} from '../stores/employees'

const employeesStore = useEmployeesStore();


</script>

<style>

</style>