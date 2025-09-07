
public abstract class Cliente {
    
    private List<Tiquete> tiquetesSinUsar;
    private List<Tiquete> tiquetesUsados;

    public Cliente() {
        this.tiquetesSinUsar = new ArrayList<>();
        this.tiquetesUsados = new ArrayList<>();
    }

    public abstract String getTipoCliente();
    public abstract String getIdentificador();

    public void agregarTiquete(Tiquete tiquete) {
        if (tiquete != null) {
            tiquetesSinUsar.add(tiquete);
        }
    }

    public int calcularValorTotalTiquetes() {
        int total = 0;
        for (Tiquete t : tiquetesSinUsar) {
            total += t.getTarifa();   
        }
        return total;
    }

    public void usarTiquetes(Vuelo vuelo) {
        Iterator<Tiquete> it = tiquetesSinUsar.iterator();
        while (it.hasNext()) {
            Tiquete t = it.next();
            if (t.getVuelo().equals(vuelo)) {
                t.usar();
                it.remove();
                tiquetesUsados.add(t);
            }
        }
    }
}

