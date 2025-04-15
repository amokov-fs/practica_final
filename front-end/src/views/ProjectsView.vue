<template>
  <div>
    <div class="header">
        <h1>Proyectos</h1>
        <!-- Boton para mostrar el formulario de alta --> 
        <v-btn @click = "projectsStore.showDialog()" color="#028CF5" style="position:absolute;top:10px;right:10px;">Alta proyecto</v-btn>
        <ProjectsForm v-model="projectsStore.dialog" /> <!-- muestra el formulario de alta si dialog de la store de proyectos es true --> 
    </div>
    <v-table style="border-style: ridge; width:100%;"> <!-- encabezados tabla --> 
        <thead>
        <tr style="background-color:#026fc1;">
            <th class = "text-white" style="border-style: ridge; border-color:black;border-width:thin; width:6%;">ID</th>
            <th class = "text-white" style="border-style: ridge; border-color:black;border-width:thin; width:30%;">Descripción</th>
            <th class = "text-white" style="border-style: ridge; border-color:black;border-width:thin; width:18%;">Fecha de inicio</th>
            <th class = "text-white" style="border-style: ridge; border-color:black;border-width:thin; width:18%;">Fecha de finalización</th>
            <th class = "text-white" style="border-style: ridge; border-color:black;border-width:thin; width:10%;">Lugar</th>
            <th class = "text-white" style="border-style: ridge; border-color:black;border-width:thin; width:3%;"></th>
            <th class = "text-white" style="border-style: ridge; border-color:black;border-width:thin; width:3%;"></th>
        </tr>
        </thead>
        <tbody>  <!-- Para cada proyecto se muestra su informacion --> 
        <tr
            v-for="proyecto in projectsStore.projects"
            :key="proyecto.index"
            style="background-color:#f3f7fb;"
            
        >
            <td style="border-style: ridge; border-color:black;border-width:thin;">{{proyecto.id}} &nbsp; </td>
            <td style="border-style: ridge; border-color:black;border-width:thin;">{{proyecto.descripcion}} &nbsp; </td>
            <td style="border-style: ridge; border-color:black;border-width:thin;">{{proyecto.fechaInicio}} &nbsp;</td>
            <td style="border-style: ridge; border-color:black;border-width:thin;">{{proyecto.fechaFin}} &nbsp;</td>
            <td style="border-style: ridge; border-color:black;border-width:thin;">{{proyecto.lugar}} &nbsp;</td>
            <td style="border-style: ridge; border-color:black;border-width:thin;">
                <v-btn icon color="error" @click="projectsStore.deleteProject(proyecto)" style="width: 30px; height: 30px;">
                  <v-icon>mdi-close</v-icon>
                </v-btn>
            </td>
            <td style="border-style: ridge; border-color:black;border-width:thin;">
                <v-btn @click = "projectsStore.showEditDialog(proyecto.id)" icon style="width: 30px; height: 30px;">
                  <v-icon>mdi-pencil</v-icon>
                </v-btn>
            </td>
        </tr>
        </tbody>
    </v-table>
    <!-- muestra el formulario de alta si editDialog de la store de proyectos es true --> 
    <EditProject v-model="projectsStore.editDialog" />
    
    <div >
        
    </div>
  </div>
  
</template>

<script setup>
//importar store de proyectos y formularios de edicion y de alta

import ProjectsForm from "../components/ProjectsForm.vue"
import EditProject from "../components/EditProject.vue"
import {useProjectsStore} from '../stores/projects'

const projectsStore = useProjectsStore();

</script>

<style>

</style>