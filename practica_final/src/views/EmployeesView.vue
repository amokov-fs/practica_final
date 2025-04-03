<template>
  <div>
    <div class="header">
        <h1>Vista empleados</h1>
        <v-btn color="#028CF5" style="position:absolute;top:50px;right:10px;">Alta empleado</v-btn>
    </div>
    
    <v-table style="border-style: ridge;">
        <thead>
        <tr style="background-color:#d4f1ff;">
            <th style="border-style: ridge; border-color:black;border-width:thin;">NIF</th>
            <th style="border-style: ridge; border-color:black;border-width:thin;">Nombre</th>
            <th style="border-style: ridge; border-color:black;border-width:thin;">1º Apellido</th>
            <th style="border-style: ridge; border-color:black;border-width:thin;">2º Apellido</th>
            <th style="border-style: ridge; border-color:black;border-width:thin;">Fecha de nacimiento</th>
            <th style="border-style: ridge; border-color:black;border-width:thin;">1º Telefono</th>
            <th style="border-style: ridge; border-color:black;border-width:thin;">Email</th>
            <th style="border-style: ridge; border-color:black;border-width:thin;">Estado Civil</th>
            <th style="border-style: ridge; border-color:black;border-width:thin;">Formacion universitaria</th>
            <th style="border-style: ridge; border-color:black;border-width:thin;"></th>
        </tr>
        </thead>
        <tbody>
        <tr
            v-for="empleado in employees"
            :key="empleado.index"
            style="background-color:#028CF5;"
            
        >
            <td class = "text-white" style="border-style: ridge; border-color:black;border-width:thin;">{{empleado.nif}} &nbsp; </td>
            <td class = "text-white" style="border-style: ridge; border-color:black;border-width:thin;">{{empleado.nombre}} &nbsp;</td>
            <td class = "text-white" style="border-style: ridge; border-color:black;border-width:thin;">{{empleado.apellido1}} &nbsp;</td>
            <td class = "text-white" style="border-style: ridge; border-color:black;border-width:thin;">{{empleado.apellido2}} &nbsp;</td>
            <td class = "text-white" style="border-style: ridge; border-color:black;border-width:thin;">{{empleado.fechaNacimiento}} &nbsp;</td>
            <td class = "text-white" style="border-style: ridge; border-color:black;border-width:thin;">{{empleado.ntelefono1}} &nbsp;</td>
            <td class = "text-white" style="border-style: ridge; border-color:black;border-width:thin;">{{empleado.email}} &nbsp;</td>
            <td class = "text-white" style="border-style: ridge; border-color:black;border-width:thin;">{{empleado.ecivil}} &nbsp;</td>
            <td class = "text-white" style="border-style: ridge; border-color:black;border-width:thin;">{{empleado.formacionU}} &nbsp;</td>
            <td class = "text-white" style="border-style: ridge; border-color:black;border-width:thin;"><v-btn color="#028CF5">X</v-btn></td>
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

const employees = ref([])
onMounted(async () => {
    
  await axios
    .get('http://localhost:8080/getEmployees')
    .then(response => {
      employees.value = response.data
    })
   
})

</script>

<style>

</style>