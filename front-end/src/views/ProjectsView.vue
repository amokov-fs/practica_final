<template>
  <div>
    <div class="header">
        <h1>Proyectos</h1>
        <v-btn @click = "projectsStore.showDialog()" color="#028CF5" style="position:absolute;top:10px;right:10px;">Alta proyecto</v-btn>
        <ProjectsForm v-model="projectsStore.dialog" />
    </div>
    <v-table style="border-style: ridge; width:100%;">
        <thead>
        <tr style="background-color:#026fc1;">
            <th class = "text-white" style="border-style: ridge; border-color:black;border-width:thin; width:40%;">Descripción</th>
            <th class = "text-white" style="border-style: ridge; border-color:black;border-width:thin; width:22%;">Fecha de inicio</th>
            <th class = "text-white" style="border-style: ridge; border-color:black;border-width:thin; width:22%;">Fecha de finalización</th>
            <th class = "text-white" style="border-style: ridge; border-color:black;border-width:thin; width:11%;">Lugar</th>
            <th class = "text-white" style="border-style: ridge; border-color:black;border-width:thin; width:5%;"></th>
            <th class = "text-white" style="border-style: ridge; border-color:black;border-width:thin; width:5%;"></th>
        </tr>
        </thead>
        <tbody>
        <tr
            v-for="proyecto in projectsStore.projects"
            :key="proyecto.index"
            style="background-color:#f3f7fb;"
            
        >
            <td style="border-style: ridge; border-color:black;border-width:thin;">{{proyecto.descripcion}} &nbsp; </td>
            <td style="border-style: ridge; border-color:black;border-width:thin;">{{proyecto.fechaInicio}} &nbsp;</td>
            <td style="border-style: ridge; border-color:black;border-width:thin;">{{proyecto.fechaFin}} &nbsp;</td>
            <td style="border-style: ridge; border-color:black;border-width:thin;">{{proyecto.lugar}} &nbsp;</td>
            <td style="border-style: ridge; border-color:black;border-width:thin;">
                <v-btn icon color="error" @click="projectsStore.deleteProject(proyecto)" style="width: 40px; height: 40px;">
                  <v-icon>mdi-close</v-icon>
                </v-btn>
            </td>
            <td style="border-style: ridge; border-color:black;border-width:thin;">
                <v-btn @click = "projectsStore.showEditDialog(proyecto.id)" icon style="width: 40px; height: 40px;">
                  <v-icon>mdi-pencil</v-icon>
                </v-btn>
            </td>
        </tr>
        </tbody>
    </v-table>

    <EditProject v-model="projectsStore.editDialog" />
    
    <div >
        
    </div>
  </div>
  
</template>

<script setup>

import {onMounted, ref, shallowRef} from "vue"
import axios from "axios"
import ProjectsForm from "../components/ProjectsForm.vue"
import EditProject from "../components/EditProject.vue"
import {useProjectsStore} from '../stores/projects'

const projectsStore = useProjectsStore();

</script>

<style>

</style>