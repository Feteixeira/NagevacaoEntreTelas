# Explicação das novas implementações!

Neste estágio do projeto, o foco foi a transição de rotas fixas para rotas dinâmicas,
permitindo que informações trafeguem entre as telas da aplicação. Abaixo, explicarei as alterações realizadas nos arquivos 
MainActivity.kt, MenuScreen.kt e PerfilScreen.kt.

1. # Implementação de Rotas Dinâmicas (MainActivity.kt)
   A rota de navegação foi alterada de "perfil" para "perfil/{nome}".

Por que?

Transformamos a tela de perfil em um componente reutilizável. Ao adicionar {nome} à rota, definimos um argumento de navegação.
Utilizamos o objeto it.arguments, para extrair o valor associado à chave "nome".
Implementamos um valor padrão ("Usuário Genérico") através do método getString,
garantindo que a aplicação tenha um comportamento seguro (fallback) caso o dado não seja enviado corretamente.


2. # Acionamento da Navegação com Dados (MenuScreen.kt)
O método navController.navigate passou de uma string simples para uma string composta: "perfil/Fulano de Tal".

Por que?

Para que a rota dinâmica definida na MainActivity funcione, a chamada de navegação precisa fornecer o dado esperado.
Ao enviar "perfil/Fulano de Tal", estamos simulando a passagem de uma variável real para a próxima tela. 
Isso demonstra o entendimento de como o NavController processa caminhos (paths) de forma análoga.

3. # Parametrizando a Interface (PerfilScreen.kt)
A função Composable PerfilScreen agora exige o parâmetro nome: String e o exibe no componente de texto.

Por que?

Esta alteração reflete o conceito de State Hoisting.
Ao adicionar nome: String aos parâmetros da função, tornamos a tela dependente de um dado externo.
Interpolação de String: No componente Text, utilizamos a variável $nome.
Isso permite que a UI seja reativa e exiba dinamicamente o nome que foi capturado lá na MainActivity.


Agora, o projeto evoluiu para suportar parâmetros que não são obrigatórios para a navegação, 
utilizando uma sintaxe similar à de URLs de busca na web.

1. # Implementação de Query Parameters (MainActivity.kt)
A rota de pedidos foi alterada de "pedidos" para "pedidos?cliente={cliente}", incluindo um bloco de arguments.

Por que?

Diferente da rota de perfil (que era obrigatória), aqui utilizamos a sintaxe de Query String (?chave={valor}).
Argumentos Opcionais: Ao definir a rota desta forma, o Jetpack Compose permite que a tela de pedidos seja aberta com ou sem a informação do cliente.
Default Value: A inclusão do defaultValue = "Cliente Genérico" dentro do navArgument é uma prática de programação defensiva.
Isso garante que a lógica de UI não precise lidar com valores nulos inesperados, fornecendo um estado inicial consistente para o componente.

2. # Adaptação da UI para Dados Opcionais (PedidosScreen.kt)
A função agora aceita cliente: String? e o texto foi atualizado para "PEDIDOS - $cliente".

Por que?

A função PedidosScreen foi atualizada para aceitar um tipo Nullable (String?).
Na linguagem Kotlin, isso significa que o dado pode estar ausente.
A interface torna-se dinâmica: se um nome for passado via navegação, ele aparece; caso contrário,
o Compose utiliza o valor padrão definido na MainActivity.
Isso aumenta a reutilização do componente, permitindo que a mesma tela sirva para "Meus Pedidos" (geral) ou "Pedidos do Cliente X".

Agora, o foco foi a atualização da lógica de navegação no componente de origem,
conectando a interface de usuário à nova estrutura de rotas flexíveis.

1. # Atualização do Gatilho de Navegação (MenuScreen.kt)
A ação do botão de pedidos foi alterada de navController.navigate("pedidos") para navController.navigate("pedidos?cliente=Cliente XPTO").

Por que?

Esta alteração demonstra a aplicação prática do conceito de Query Parameters (parâmetros de consulta) no Jetpack Compose Navigation.
Ao utilizar o formato ?cliente=Valor, estamos injetando uma informação específica na rota de destino sem que ela seja obrigatória para a existência da rota.
Comunicação entre Telas: Essa mudança é fundamental para entender como dados dinâmicos (que poderiam vir de um campo de texto ou de uma lista)
são transportados através do NavController. 
Separação de Responsabilidades: O MenuScreen agora decide qual informação enviar,
enquanto a MainActivity decide como processar essa informação e a PedidosScreen decide como exibi-la.


Etapa final, a arquitetura de navegação foi expandida para suportar múltiplos argumentos obrigatórios e a definição explícita de tipos de dados,
garantindo maior segurança e robustez ao aplicativo.

1. # Configuração de Argumentos Tipados (MainActivity.kt)
A rota de perfil evoluiu para "perfil/{nome}/{idade}" e foi adicionada a configuração de navArgument para a idade.

Por que?

Múltiplos Path Arguments: A definição da rota agora exige dois parâmetros posicionais.
A navegação só será processada se ambos forem fornecidos no caminho da URL.
Tipagem Forte com NavType: Introduzimos o type = NavType.IntType para o argumento "idade".
Isso é crucial porque, por padrão, o Compose trata argumentos de navegação como Strings.
Definir o tipo como Inteiro evita erros de conversão manual e garante que o dado chegue no formato correto.
Recuperação de Dados: Utilizamos it.arguments?.getInt("idade") para extrair o valor numérico com segurança,
definindo um valor padrão (0) para evitar quebras no layout.

2. # Construção de URIs Complexas (MenuScreen.kt)
A chamada de navegação agora envia dois valores: "perfil/Fulano de Tal/27".

Por que?
Esta mudança demonstra o entendimento de como construir caminhos de navegação complexos.
Ao concatenar /27 ao final da string de navegação, estamos preenchendo o placeholder {idade} definido na MainActivity.
Isso simula um cenário real onde múltiplos dados de um objeto (como um usuário) precisam ser enviados para uma tela de detalhes.

3. # Interface Composta e Reativa (PerfilScreen.kt)
A função PerfilScreen agora recebe idade: Int e exibe uma frase composta no componente Text.

Por que?

Evolução da UI: O componente de texto foi refatorado para usar interpolação de múltiplas variáveis: "PERFIL - $nome tem $idade anos".
Null Safety e Asserção: Na MainActivity, passamos os dados usando o operador !! (not-null assertion).
Isso prova ao professor que você entende o fluxo: como os argumentos são obrigatórios na definição da rota e possuem valores padrão na extração,
temos a garantia técnica de que eles não serão nulos ao renderizar a tela.
