<template>
    <div>
        <b-modal hide-footer hide-header centered id="modal-update-movie">
            <header class="text-center border-bottom">
                <p style="font-family: cabin">Editar película</p>
            </header>

            <main>
                <form id="editarPelicula" @submit.prevent="updateMovie">
                    <b-row>
                        <b-col>
                            <label for="pelicula">Nombre de la película: *</label>
                            <b-form-input v-model="editedMovie.name" type="text" class="form-control"
                                placeholder="Película..." required :state="validarName"></b-form-input>
                            <b-form-invalid-feedback :state="validarName">
                                Formato invalido
                            </b-form-invalid-feedback>
                        </b-col>
                        <b-col>
                            <label for="pelicula">Género de la película: *</label>
                            <b-form-select v-model="editedMovie.genero" :options="options"
                                :state="validarGenero"></b-form-select>
                            <b-form-invalid-feedback :state="validarGenero">
                                Selecciona un género válido
                            </b-form-invalid-feedback>
                        </b-col>
                    </b-row>
                    <b-row>
                        <b-col>
                            <label for="pelicula">Descripción de la película: *</label>
                            <b-form-textarea v-model="editedMovie.description" id="textarea"
                                placeholder="Describe la película..." rows="3" max-rows="6"
                                :state="validarDescription"></b-form-textarea>
                            <b-form-invalid-feedback :state="validarDescription">
                                Formato invalido (Coloca el puntero en el icono de abajo para
                                saber los lineamientos del formato)
                            </b-form-invalid-feedback>
                        </b-col>
                    </b-row>
                    <b-button type="submit" class="d-none"></b-button>
                </form>
            </main>

            <footer class="text-center mt-5">
                <button class="btn m-1 cancel" @click="onClose" id="savemovie">
                    Cancelar
                </button>
                <button class="btn m-1 success" @click="updateMovie" :disabled="!validarForm" type="submit">
                    Actualizar
                </button>
            </footer>
        </b-modal>
    </div>
</template>

<script>
import Swal from 'sweetalert2';
import axios from 'axios';

export default {
    name: 'ModalUpdateMovie',
    props: {
        movie: Object,
    },
    data() {
        return {
            options: [
                { value: null, text: 'Selecciona una opción' },
                { value: 'Terror', text: 'Terror' },
                { value: 'Aventura', text: 'Aventura' },
                { value: 'Acción', text: 'Acción' },
                { value: 'Catástrofe', text: 'Catástrofe' },
                { value: 'Ciencia Ficción', text: 'Ciencia Ficción' },
                { value: 'Comedia', text: 'Comedia' },
                { value: 'Documentales', text: 'Documentales' },
                { value: 'Drama', text: 'Drama' },
                { value: 'Infantil', text: 'Infantil' },
            ],
            editedMovie: {
                name: '',
                genero: null,
                description: '',
            },
        };
    },
    methods: {
        onClose() {
            this.$bvModal.hide('modal-update-movie');
        },
        async updateMovie() {
            try {
                Swal.fire({
                    title: '¿Estás seguro de actualizar la película?',
                    icon: 'warning',
                    showCancelButton: true,
                    confirmButtonColor: '#008c6f',
                    cancelButtonColor: '#e11c24',
                    confirmButtonText: 'Confirmar',
                    cancelButtonText: 'Cancelar',
                }).then(async (result) => {
                    if (result.isConfirmed) {
                        console.log("Pelicula a editar: ",this.editedMovie);
                        await axios.put(`http://localhost:8080/api-movieBack/`, this.editedMovie);
                        Swal.fire({
                            title: 'Actualizada',
                            text: 'La película se actualizó correctamente',
                            icon: 'success',
                        });

                        this.onClose();
                        this.$emit('movie-updated');
                    }
                });
            } catch (error) {
                console.error('Error al actualizar la película', error);
            }
        },
    },
    watch: {
        movie: {
            handler(newVal) {
                this.editedMovie = { ...newVal };
            },
            immediate: true,
        },
    },
    computed: {
        validarName() {
            const regex = /^(?!.*[\s]{2,})(?!^\s)(?!.*\s$)(?!.*(\S)\1{2,})[a-zA-Z0-9\s\-_.,]*$/;
            return (
                regex.test(this.editedMovie.name)
            );
        },
        validarDescription() {
            const regex = /^(?!.*[\s]{2,})(?!^\s)(?!.*\s$)(?!.*(\S)\1{2,})[a-zA-Z0-9\s\-_.,]*$/;
            return (
                regex.test(this.editedMovie.description)
            );
        },
        validarGenero() {
            return this.editedMovie.genero !== null;
        },
        validarForm() {
            return (
                this.validarName && this.validarDescription && this.validarGenero
            );
        },
    },
};
</script>
  
<style scoped>
.success {
    font-family: Cabin;
    background-color: #009475;
    color: white;
}

.cancel {
    font-family: Cabin;
    background-color: #ffce50;
    color: black;
}
</style>
  