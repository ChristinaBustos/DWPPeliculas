<template>
    <div>
        <div>
            <b-modal hide-footer hide-header centered id="modal-save-movie">
                <header class="text-center border-bottom">
                    <p style="font-family: cabin">Registrar pelicula</p>
                </header>

                <main>
                    <form id="registrarPelicula">
                        <b-row>
                            <b-col>
                                <label for="pelicula">Nombre de la pelicula: *</label>
                                <b-form-input v-model="pelicula.name" type="text" class="form-control"
                                    placeholder="Pelicula..." required
                                    aria-describedby="input-live-help input-live-feedback" />
                            </b-col>
                            <b-col>
                                <label for="pelicula">Genero de la pelicula: *</label>
                                <b-form-select v-model="pelicula.genero" :options="options"></b-form-select>
                            </b-col>
                        </b-row>
                        <b-row>
                            <b-col>
                                <label for="pelicula">Descripción de la pelicula: *</label>
                                <b-form-textarea id="textarea" v-model="pelicula.description"
                                    placeholder="Describe la pelicula..." rows="3" max-rows="6"></b-form-textarea>
                            </b-col>
                        </b-row>
                    </form>

                </main>

                <footer class="text-center mt-5">
                    <button class="btn m-1 cancel" @click="onClose" id="savemovie">
                        Cancelar
                    </button>
                    <button class="btn m-1 success" @click="save" id="saveteam" type="submit">
                        Registrar
                    </button>
                </footer>
            </b-modal>
        </div>
    </div>
</template>

<script>
import Swal from 'sweetalert2';

export default {
    name: "modal-save-movie",

    data() {
        return {
            pelicula: {
                name: "",
                description: "",
                genero: null,
            },
            selected: null,
            options: [
                { value: null, text: "Selecciona una opción" },
                { value: "Terror", text: "Terror" },
                { value: "Aventura", text: "Aventura" },
                { value: "Acción", text: "Acción" },
                { value: "Catástrofe", text: "Catástrofe" },
                { value: "Ciencia Ficción.", text: "Ciencia Ficción." },
                { value: "Comedia", text: "Comedia" },
                { value: "Documentales", text: "Documentales" },
                { value: "Drama", text: "Drama" },
            ],
            
        };
    },
    methods: {
        onClose() {
            this.$bvModal.hide("modal-save-movie");
            this.pelicula.name = ""
            this.pelicula.description = ""
            this.pelicula.genero = null

        },
        async save() {
            Swal.fire({
                title: "¿Estás seguro de registrar la pelicula?",
                icon: 'warning',
                showCancelButton: true,
                confirmButtonColor: '#008c6f',
                cancelButtonColor: '#e11c24',
                confirmButtonText: "Confirmar",
                cancelButtonText: 'Cancelar',
            }).then(async (result) => {
                if (result.isConfirmed) {
                    try {
                        console.log(this.pelicula);
                        Swal.fire({
                            title: "¡Guardada!",
                            text: "La pelicula se registró correctamente",
                            icon: "success"
                        });
                        this.pelicula.name = ""
                        this.pelicula.description = ""
                        this.pelicula.genero = null
                        this.onClose();
                    } catch (error) {

                    }

                }
            });
        },

    },

}
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