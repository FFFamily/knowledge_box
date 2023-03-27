package com.tutu.knowledge_box.ai.openai.completion;

import com.fasterxml.jackson.annotation.JsonProperty;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.List;
import java.util.Map;

/**
 * A request for OpenAi to generate a predicted completion for a prompt.
 * All fields are nullable.
 *
 * https://beta.openai.com/docs/api-reference/completions/create
 */
@Builder
@NoArgsConstructor
@AllArgsConstructor
@Data
public class CompletionRequest {

    /**
     *要使用的模型的名称。
     *如果指定微调模型或使用新的v1/completions，则为必需。
     */
    String model;

    /**
     * 一个可选的提示来完成
     */
    String prompt;

    /**
     * The suffix that comes after a completion of inserted text.
     */
    String suffix;

    /**
     *要生成的最大令牌数。
     *请求最多可以使用2048个在提示和完成之间共享的令牌。
     *（对于普通英语文本，一个标记大约为4个字符）
     */
    @JsonProperty("max_tokens")
    Integer maxTokens;

    /**
     * What sampling temperature to use. Higher values means the model will take more risks.
     * Try 0.9 for more creative applications, and 0 (argmax sampling) for ones with a well-defined answer.
     *
     * We generally recommend using this or {@link CompletionRequest#topP} but not both.
     */
    Double temperature;

    /**
     * An alternative to sampling with temperature, called nucleus sampling, where the model considers the results of
     * the tokens with top_p probability mass. So 0.1 means only the tokens comprising the top 10% probability mass are
     * considered.
     *
     * We generally recommend using this or {@link CompletionRequest#temperature} but not both.
     */
    @JsonProperty("top_p")
    Double topP;

    /**
     * 为每个提示生成多少完成。
     *
     * 因为这个参数会产生很多完成，它会很快消耗你的令牌配额。
     * 小心使用并确保您对 {@link CompletionRequest#maxTokens} 和 {@link CompletionRequest#stop} 进行了合理的设置。
     */
    Integer n;

    /**
     * Whether to stream back partial progress.
     * If set, tokens will be sent as data-only server-sent events as they become available,
     * with the stream terminated by a data: DONE message.
     */
    Boolean stream;

    /**
     * Include the log probabilities on the logprobs most likely tokens, as well the chosen tokens.
     * For example, if logprobs is 10, the API will return a list of the 10 most likely tokens.
     * The API will always return the logprob of the sampled token,
     * so there may be up to logprobs+1 elements in the response.
     */
    Integer logprobs;

    /**
     * 除了完成之外回显提示
     */
    Boolean echo;

    /**
     * Up to 4 sequences where the API will stop generating further tokens.
     * The returned text will not contain the stop sequence.
     */
    List<String> stop;

    /**
     * Number between 0 and 1 (default 0) that penalizes new tokens based on whether they appear in the text so far.
     * Increases the model's likelihood to talk about new topics.
     */
    @JsonProperty("presence_penalty")
    Double presencePenalty;

    /**
     * Number between 0 and 1 (default 0) that penalizes new tokens based on their existing frequency in the text so far.
     * Decreases the model's likelihood to repeat the same line verbatim.
     */
    @JsonProperty("frequency_penalty")
    Double frequencyPenalty;

    /**
     * Generates best_of completions server-side and returns the "best"
     * (the one with the lowest log probability per token).
     * Results cannot be streamed.
     *
     * When used with {@link CompletionRequest#n}, best_of controls the number of candidate completions and n specifies how many to return,
     * best_of must be greater than n.
     */
    @JsonProperty("best_of")
    Integer bestOf;

    /**
     * Modify the likelihood of specified tokens appearing in the completion.
     *
     * Maps tokens (specified by their token ID in the GPT tokenizer) to an associated bias value from -100 to 100.
     *
     * https://beta.openai.com/docs/api-reference/completions/create#completions/create-logit_bias
     */
    @JsonProperty("logit_bias")
    Map<String, Integer> logitBias;

    /**
     * 代表您的最终用户的唯一标识符，这将有助于 OpenAI 监控和检测滥用行为。
     */
    String user;
}
