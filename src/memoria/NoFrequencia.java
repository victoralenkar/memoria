package memoria;

public class NoFrequencia {

	private int chave;
	private int frequencia;

	private NoFrequencia proximo;

	public NoFrequencia(int chave, int frequencia) {
		this.frequencia = frequencia;
		this.chave = chave;
	}

	public NoFrequencia() {
		this.chave = -1;
		this.frequencia = -1;
	}

	public int getFrequencia() {
		return frequencia;
	}

	public void setFrequencia(int frequencia) {
		this.frequencia = frequencia;
	}

	public NoFrequencia getProximo() {
		return proximo;
	}

	public void setProximo(NoFrequencia proximo) {
		this.proximo = proximo;
	}

	public int getChave() {
		return chave;
	}

	public void setChave(int valor) {
		this.chave = valor;
	}

	public void adicionar(int chave) {
		NoFrequencia primeiro = this.getProximo();
		if (primeiro != null) {
			if (primeiro.getChave() == chave) {
				primeiro.setFrequencia(primeiro.getFrequencia() + 1);
			} else {
				NoFrequencia aux = primeiro.getProximo();
				NoFrequencia anterior = primeiro;
				boolean encontrou = false;
				while (aux != null && !encontrou) {
					if (aux.getChave() == chave) {
						aux.setFrequencia(aux.getFrequencia() + 1);
						encontrou = true;
					} else {
						anterior = aux;
						aux = aux.getProximo();

					}
				}
				if (!encontrou) {
					anterior.setProximo(new NoFrequencia(chave, 1));
				}
			}
		} else {
			this.setProximo(new NoFrequencia(chave, 1));
		}

	}

	public int getChaveMenorFrequencia(int tamanhoConjunto) {
		NoFrequencia no = this.getProximo();
		int frequenciaMenor = this.getProximo() != null ? this.getProximo().getFrequencia() : 0;
		int chave = this.getProximo() != null ? this.getProximo().getChave():-1;
		int total = 0;
		while (no != null) {
			if (no.getFrequencia() < frequenciaMenor) {
				frequenciaMenor = no.getFrequencia();
				chave=no.getChave();
			}
			no = no.getProximo();
			total++;
		}
		return total<tamanhoConjunto?-1:chave;

	}

	public void atualizarChaves(Integer[] conjunto) {
		NoFrequencia aux = this.getProximo();
		NoFrequencia anterior = this;
		while (aux != null) {
			boolean encontrou = false;
			for (int i = 0; i < conjunto.length; i++) {
				if (conjunto[i] != null && conjunto[i] == aux.getChave()) {
					encontrou = true;
					break;
				}
			}
			if (!encontrou) {
				anterior.setProximo(anterior.getProximo().getProximo());
				aux = anterior.getProximo();
			} else {
				anterior = aux;
				aux = aux.getProximo();
			}
		}
	}

	@Override
	public String toString() {
		String lista = "{ ";
		NoFrequencia aux = this.getProximo();
		while (aux != null) {
			lista += "[" + aux.chave + ", " + aux.frequencia + "]; ";
			aux = aux.getProximo();
		}
		lista = lista.substring(0, lista.length() - 2) + " }";
		return lista;
	}

	public static void main(String[] args) {
		NoFrequencia head = new NoFrequencia(-1, -1);
		head.adicionar(1);
		head.adicionar(2);
		head.adicionar(3);
		head.adicionar(4);
		head.adicionar(5);
		System.out.println(head.toString());
		head.adicionar(5);
		System.out.println(head.toString());
		head.adicionar(3);
		System.out.println(head.toString());
		head.adicionar(8);
		System.out.println(head.toString());
		head.atualizarChaves(new Integer[] { 1, 2, 4, 5 });
		System.out.println(head.toString());
		System.out.println(head.getChaveMenorFrequencia(5));
		head.adicionar(1);
		System.out.println(head.getChaveMenorFrequencia(5));
		System.out.println(head.toString());
		head.adicionar(2);
		System.out.println(head.getChaveMenorFrequencia(5));
		System.out.println(head.toString());
		head.adicionar(1);
		System.out.println(head.getChaveMenorFrequencia(5));
		System.out.println(head.toString());
		head.adicionar(4);
		head.adicionar(4);
		System.out.println(head.getChaveMenorFrequencia(5));
		System.out.println(head.toString());
		head.adicionar(2);
		System.out.println(head.getChaveMenorFrequencia(5));
		System.out.println(head.toString());
		head.adicionar(5);
		System.out.println(head.getChaveMenorFrequencia(5));
		System.out.println(head.toString());
		head.adicionar(1);
		System.out.println(head.getChaveMenorFrequencia(5));
		System.out.println(head.toString());
	}
}
