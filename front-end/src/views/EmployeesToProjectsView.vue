<template>
  <div>
    <h1>Asignaciones de empleados a proyectos</h1>
    <!-- Desplegable de los proyectos --> 
    <v-select
        clearable 
        v-model = "employeesToProjectsStore.selectedProject"
        label="Selecciona Proyecto"
        :item-props="itemProps"
        :items="projectsStore.projects"
        item-title="descripcion"
        item-value="id"
        variant="outlined"
        style="height:50px; margin-bottom:15px;"
        @update:modelValue="onItemChange"
        ></v-select>
    <!-- Tabla con los empleados --> 
    <v-table style="border-style: ridge;"> 
        <thead>
        <tr style="background-color:#026fc1;color:white;">
            <th style="border-style: ridge; border-color:black;border-width:thin;">Nombre completo</th>
            <th style="border-style: ridge; border-color:black;border-width:thin;">Asignado</th>
        </tr>
        </thead>
        <tbody>
        <tr
            v-for="empleado in employeesStore.employees"
            :key="empleado.index"
            style="background-color:#f3f7fb;"
        >   <!-- Nombre y apellidos empleado --> 
            <td  style="border-style: ridge; border-color:black;border-width:thin;">{{empleado.nombre}} {{empleado.apellido1}} {{empleado.apellido2}}</td>
            <td  style="border-style: ridge; border-color:black;border-width:thin;">
                <v-checkbox
                    :model-value="employeesToProjectsStore.estaAsignado(empleado.id)"
                    @click.prevent="() => employeesToProjectsStore.modificarAsignacion(empleado.id)"
                    hide-details
                    density="compact"
                ></v-checkbox> <!-- Checkbox marcada en caso de estar asignado y que asigna/desasigna el empelado al proyecto seleccionado --> 
            </td>
        </tr>
        </tbody>
    </v-table>
  </div>
    
</template>

<script setup>

// importar stores de empleados, pproyectos y asignaciones

import {onMounted} from 'vue'
import axios from "axios"
import {useEmployeesToProjectsStore} from '../stores/employeesToProjects'
import {useProjectsStore} from '../stores/projects'
import {useEmployeesStore} from '../stores/employees'

const projectsStore = useProjectsStore();
const employeesToProjectsStore = useEmployeesToProjectsStore()
const employeesStore = useEmployeesStore();

// al montar las stores, se llenan la lista de empleados y el diccionario de asignaciones (lista de proyectos no hace falta)

onMounted(async () => {
    await employeesStore.getActiveEmployees()
    await employeesToProjectsStore.getAsignacionesProyecto(employeesToProjectsStore.selectedProject)
          
})

function itemProps (project) { // mostrar solo la descripcion del proyecto en el desplegable
    return {
      title: project.descripcion
    }
}

const onItemChange = () => { // al cambiar de proyecto se buscan sus empleados asignados
    employeesToProjectsStore.getAsignacionesProyecto(employeesToProjectsStore.selectedProject)
}

</script>

<style>

</style>