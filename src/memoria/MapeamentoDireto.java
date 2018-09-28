package memoria;

public class MapeamentoDireto extends Cache {

	public MapeamentoDireto(int tamanho) {
		super.cache = new Integer[tamanho];
		super.politica = PoliticaInsercaoEnum.NENHUM;
	}

	public boolean isHit(int pagina) {
		return (cache[pagina % super.cache.length] != null && cache[pagina % super.cache.length].intValue() == pagina);
	}

	public void inserir(int pagina) {
		int mod = pagina % super.cache.length;
		super.cache[mod] = pagina;
	}

}
