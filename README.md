# memoria
Implementação de algoritmos de substituição de página de memória

## Executando o programa...
java -jar memoria.jar {arquivo_entrada} -t {tipo_mapeamento} -s {tamanho_cache} -c {qtde_conjuntos} -p {politica_substituicao}

{tipo_mapeamento}  = [(D)ireto, Associativo (T)otal, Associativo por (C)onjunto];

{qtde_conjuntos} = Obrigatório apenas em caso de -t C (Associativo por Conjunto);

{politica_substituicao} = [RANDOM, FIFO , LRU, LFU];
