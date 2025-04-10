<template>
  <div>
    <div class="header">
        <h1>Empleados</h1>
        <v-btn @click = "employeesStore.showDialog()" color="#028CF5" style="position:absolute;top:10px;right:10px;">Alta empleado</v-btn>
        <EmployeesForm v-model="employeesStore.dialog" />
    </div>
    
    <v-table style="border-style: ridge;">
        <thead>
        <tr style="background-color:#026fc1;color:white;">
            <th style="border-style: ridge; border-color:black;border-width:thin; width:10%;">NIF</th>
            <th style="border-style: ridge; border-color:black;border-width:thin; width:10%;">Nombre</th>
            <th style="border-style: ridge; border-color:black;border-width:thin; width:10%;">1º Apellido</th>
            <th style="border-style: ridge; border-color:black;border-width:thin; width:10%;">2º Apellido</th>
            <th style="border-style: ridge; border-color:black;border-width:thin; width:10%;">Fecha de nacimiento</th>
            <th style="border-style: ridge; border-color:black;border-width:thin; width:10%;">1º Telefono</th>
            <th style="border-style: ridge; border-color:black;border-width:thin; width:15%;">Email</th>
            <th style="border-style: ridge; border-color:black;border-width:thin;width:10%;">Estado Civil</th>
            <th style="border-style: ridge; border-color:black;border-width:thin;width:11%;">Formación universitaria</th>
            <th style="border-style: ridge; border-color:black;border-width:thin;width:4%;"></th>
        </tr>
        </thead>
        <tbody>
        <tr
            v-for="empleado in employeesStore.employees"
            :key="empleado.id"
            style="background-color:#f3f7fb;"
            
        >
            <td style="border-style: ridge; border-color:black;border-width:thin;">{{empleado.nif}} &nbsp; </td>
            <td style="border-style: ridge; border-color:black;border-width:thin;">{{empleado.nombre}} &nbsp;</td>
            <td style="border-style: ridge; border-color:black;border-width:thin;">{{empleado.apellido1}} &nbsp;</td>
            <td style="border-style: ridge; border-color:black;border-width:thin;">{{empleado.apellido2}} &nbsp;</td>
            <td style="border-style: ridge; border-color:black;border-width:thin;">{{empleado.fechaNacimiento}} &nbsp;</td>
            <td style="border-style: ridge; border-color:black;border-width:thin;">{{empleado.ntelefono1}} &nbsp;</td>
            <td style="border-style: ridge; border-color:black;border-width:thin;">{{empleado.email}} &nbsp;</td>
            <td v-if="empleado.ecivil == 'S'" style="border-style: ridge; border-color:black;border-width:thin;">Soltero</td>
            <td v-if="empleado.ecivil == 'C'" style="border-style: ridge; border-color:black;border-width:thin;">Casado</td>
            <td v-if="empleado.formacionU == 'S'" style="border-style: ridge; border-color:black;border-width:thin;">Si</td>
            <td v-if="empleado.formacionU == 'N'" style="border-style: ridge; border-color:black;border-width:thin;">No</td>
            <td style="border-style: ridge; border-color:black;border-width:thin;">
                <v-btn icon color="error" @click="employeesStore.deleteEmployee(empleado)" style="width: 40px; height: 40px;">
                  <v-icon>mdi-close</v-icon>
                </v-btn>
            </td>
        </tr>
        </tbody>
    </v-table>
    
    <div >
        
    </div>
  </div>
  
</template>

<script setup>

import {onMounted, ref} from "vue"
import axios from "axios"
import {useEmployeesStore} from '../stores/employees'
import EmployeesForm from '../components/EmployeesForm.vue'

const employeesStore = useEmployeesStore();

onMounted(() => {
  if (employeesStore.employees.length === 0) {
    employeesStore.getActiveEmployees()
  }
})

</script>

<style>

</style>