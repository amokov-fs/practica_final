<template>
  <div>
    <h1>Hola</h1>
    <v-select
        clearable
        v-model = "selectedProject"
        label="Selecciona Proyecto"
        :item-props="itemProps"
        :items="projects"
        item-title="descripcion"
        item-value="id"
        variant="outlined"
        style="height:50px; margin-bottom:15px;"
        @update:modelValue="onItemChange"
        ></v-select>

    <v-table style="border-style: ridge;">
        <thead>
        <tr style="background-color:#026fc1;color:white;">
            <th style="border-style: ridge; border-color:black;border-width:thin;">Nombre completo</th>
            <th style="border-style: ridge; border-color:black;border-width:thin;"></th>
        </tr>
        </thead>
        <tbody>
        <tr
            v-for="empleado in employees"
            :key="empleado.index"
            style="background-color:#f3f7fb;"
        >
            <td  style="border-style: ridge; border-color:black;border-width:thin;">{{empleado.nombre}} {{empleado.apellido1}} {{empleado.apellido2}}</td>
            <td  style="border-style: ridge; border-color:black;border-width:thin;">
                <v-checkbox
                    :model-value="estaAsignado(empleado)"
                    @change="() => toggleAsignacion(empleado)"
                    hide-details
                    density="compact"
                ></v-checkbox>
            </td>
        </tr>
        </tbody>
    </v-table>
  </div>
    
</template>

<script setup>

import {ref, shallowRef, onMounted, reactive} from 'vue'
import axios from "axios"

const projects = ref([])
const employees = ref([])
const selectedProject = ref(null)


onMounted(async () => {
    
    await axios
    .get('http://localhost:8080/getProjects')
    .then(response => {
      projects.value = response.data
    }),
    await axios.get('http://localhost:8080/getEmployees')
    .then(responseEmployees => {
      employees.value = responseEmployees.data
    }) 
    
      
})

function itemProps (project) { 
    return {
      title: project.descripcion
    }
}

const onItemChange = () => {
    employeesProject()
}

async function employeesProject () {
    const responseProjectEmployees = await axios.get('http://localhost:8080/getProjectEmployees?idProyecto=' + selectedProject.value);
    
    let employeesProject = []
    for (const employee in responseProjectEmployees.data) {
        employeesProject.push(responseProjectEmployees.data[employee].id)
    }
    asignaciones[selectedProject.value] = employeesProject
    
}


// Asignaciones: { [proyectoId]: [emails de usuarios] }
const asignaciones = reactive({})

// Saber si un usuario está asignado al proyecto seleccionado
const estaAsignado = (empleado) => {
  const asignados = asignaciones[selectedProject.value] || [];
  return asignados.includes(empleado.id);
};

// Agregar o quitar asignación
const toggleAsignacion = (empleado) => {
  const id = selectedProject.value;
  if (!id) return;

  // Si no existe, inicializa el array
  if (!Array.isArray(asignaciones[id])) {
    asignaciones[id] = [];
  }

  const index = asignaciones[id].indexOf(empleado.id);

  if (index === -1) {
    // Asignar
    asignaciones[id].push(empleado.id);
    guardarAsignacionEnBD(empleado.id)
  } else {
    // Desasignar
    asignaciones[id].splice(index, 1);
    eliminarAsignacionEnBD(empleado.id);
  }
};

async function guardarAsignacionEnBD(idEmpleado) {
  const responseProjectEmployees = await axios.post('http://localhost:8080/assignEmployeeToProject?idProyecto=' + selectedProject.value+'&idEmpleado='+idEmpleado);
}

async function eliminarAsignacionEnBD(idEmpleado) {
  const responseProjectEmployees = await axios.delete('http://localhost:8080/deleteEmployeeFromProject?idProyecto=' + selectedProject.value+'&idEmpleado='+idEmpleado);
}



</script>

<style>

</style>