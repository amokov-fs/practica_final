import { ref, computed } from 'vue'
import { defineStore } from 'pinia'


export const useEmployeesStore = defineStore('employees', {
  state: () => ({ // lista para almacenar los pokemons
    employees: []
  }),
  actions: {
    // funcion para llenar la lista de pokemons en base a un offset y un limite en los IDs de los pokemon
    /*employeesList () { 
        if (this.employees.length === 0) {
            for (var i = offsetNum+1; i < (offsetNum + limitNum+1); i++) {
                this.pokemons.push(i.toString())
            }
        } else {
            this.employees = []
        }
    },

    
    addPokemon (name) { // funcion para añadir un pokemon por su nombre
        let nameLow = name.toLowerCase();
        this.pokemons.push(nameLow);
    },
    deletePokemon (id) { // funcion para eliminar un pokemon por su nombre/id
        console.log(this.pokemons)
        let index = this.pokemons.indexOf(id);
        console.log(index)
        this.pokemons.splice(index, 1);
    }*/
  }
})