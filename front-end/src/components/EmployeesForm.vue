<template>
  <v-dialog v-model="employeesStore.dialog" max-width="700px">
    <v-card>
        <v-card-text> <!-- muestra el formulario si dialog de la store de empleados es true --> 
            <v-row dense>
                <v-col md="6">
                    <v-text-field
                    v-model = "nif"
                    label="NIF*"
                    required
                    ></v-text-field>
                </v-col> <!-- Input NIF --> 
                
                <v-col md="6">
                    <v-text-field
                    v-model = "nombre"
                    label="Nombre*"
                    required
                    ></v-text-field> <!-- Input nombre --> 
                </v-col>
            </v-row>
            <v-row dense>
                <v-col md="6">
                    <v-text-field
                    v-model = "ap1"
                    label="Apellido 1*"
                    required
                    ></v-text-field> <!-- Input apellido1 --> 
                </v-col>
                <v-col md="6">
                    <v-text-field
                    v-model = "ap2"
                    label="Apellido 2*"
                    required
                    ></v-text-field><!-- Input apellido2 --> 
                </v-col>
            </v-row>
            <v-row dense>
                <v-col md="6">
                    <v-text-field
                    v-model = "fNacimiento"
                    label="Fecha de nacimiento* (yyyy-mm-dd)"
                    required
                    ></v-text-field> <!-- Input fecha nacimiento --> 
                </v-col>
                <v-col md="6">
                    <v-text-field
                    v-model = "fAlta"
                    label="Fecha de Alta* (yyyy-mm-dd)"
                    required
                    ></v-text-field> <!-- Input fecha alta --> 
                </v-col>
            </v-row>
            <v-row dense>
                <v-col md="6">
                    <v-text-field
                    v-model = "tlf1"
                    label="Telefono 1*"
                    required
                    ></v-text-field> <!-- Input telefono 1 --> 
                </v-col>
                <v-col md="6">
                    <v-text-field
                    v-model = "tlf2"
                    label="Telefono 2"
                    ></v-text-field> <!-- Input telefono2 --> 
                </v-col>
            </v-row>
            <v-row dense>
                <v-col md="12">
                    <v-text-field
                    v-model = "email"
                    label="Email*"
                    required
                    ></v-text-field>
                </v-col> <!-- Input email --> 
            </v-row>
            <v-row dense>
                <v-col md="6">
                    <v-text-field
                    v-model = "edoCivil"
                    label="Estado Civil* (S - Soltero/a; C- Casado/a)"
                    required
                    ></v-text-field> <!-- Input estado civil --> 
                </v-col>
                <v-col md="6">
                    <v-text-field
                    v-model = "formacionU"
                    label="Formacion Universitaria* (S-Si; N-No)"
                    required
                    ></v-text-field> <!-- Input formacion universitaria --> 
                </v-col>
            </v-row>

            <small class="text-caption text-medium-emphasis">*campo requerido</small>
            </v-card-text>
        <v-card-actions>
            <v-spacer></v-spacer>
            <!-- Boton de cancelar, cierra el formulario de alta sin guardar --> 
            <v-btn text="Cancelar" variant="plain" @click.stop="employeesStore.hideDialog()"></v-btn>
            <!-- boton de guardar el empleado, se le pasa un diccionario con el empleado nuevo --> 
            <v-btn
                color="primary"
                text="Guardar"
                variant="tonal"
                @click ="employeesStore.saveNewEmployee({
                    'apellido1': ap1,
                    'apellido2': ap2,
                    'ecivil': edoCivil,
                    'email': email,
                    'fechaAlta': fAlta,
                    'fechaNacimiento': fNacimiento,
                    'formacionU': formacionU,
                    'nif': nif,
                    'nombre': nombre,
                    'telefono1': tlf1,
                    'telefono2': tlf2
                })"
            ></v-btn>
        </v-card-actions>
    </v-card>
  </v-dialog>
</template>


<script setup>

//Importar store de empleados

import {ref} from 'vue'
import {useEmployeesStore} from '../stores/employees'

// Variables que actualizan los inputs

const nif = ref("")
const nombre = ref("")
const ap1 = ref("")
const ap2 = ref("")
const fNacimiento = ref("")
const fAlta = ref("")
const tlf1 = ref("")
const tlf2 = ref("")
const email = ref("")
const edoCivil = ref("")
const formacionU = ref("")


const employeesStore = useEmployeesStore();



</script>

<style>

</style>