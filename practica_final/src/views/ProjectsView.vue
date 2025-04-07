<template>
  <div>
    <div class="header">
        <h1>Proyectos</h1>
        <v-btn color="#028CF5" style="position:absolute;top:10px;right:10px;">Alta proyecto</v-btn>
    </div>
    <v-table style="border-style: ridge; width:100%;">
        <thead>
        <tr style="background-color:#026fc1;">
            <th class = "text-white" style="border-style: ridge; border-color:black;border-width:thin; width:40%;">Descripción</th>
            <th class = "text-white" style="border-style: ridge; border-color:black;border-width:thin; width:22%;">Fecha de inicio</th>
            <th class = "text-white" style="border-style: ridge; border-color:black;border-width:thin; width:22%;">Fecha de finalización</th>
            <th class = "text-white" style="border-style: ridge; border-color:black;border-width:thin; width:11%;">Lugar</th>
            <th class = "text-white" style="border-style: ridge; border-color:black;border-width:thin; width:5%;"></th>
        </tr>
        </thead>
        <tbody>
        <tr
            v-for="proyecto in projects"
            :key="proyecto.index"
            style="background-color:#f3f7fb;"
            
        >
            <td style="border-style: ridge; border-color:black;border-width:thin;">{{proyecto.descripcion}} &nbsp; </td>
            <td style="border-style: ridge; border-color:black;border-width:thin;">{{proyecto.fechaInicio}} &nbsp;</td>
            <td style="border-style: ridge; border-color:black;border-width:thin;">{{proyecto.fechaFin}} &nbsp;</td>
            <td style="border-style: ridge; border-color:black;border-width:thin;">{{proyecto.lugar}} &nbsp;</td>
            <td style="border-style: ridge; border-color:black;border-width:thin;">
                <v-btn icon color="error" @click="accionX" style="width: 40px; height: 40px;">
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

const projects = ref([])
onMounted(async () => {
    
  await axios
    .get('http://localhost:8080/getProjects')
    .then(response => {
      projects.value = response.data
    })
   
})

</script>

<style>

</style>