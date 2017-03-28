/**
 * Created by wong on 28/03/17.
 */

const elm = React.createElement;

const Autocomplete = React.createClass({

    getInitialState: function () {
        return {
            words: null
        };
    },

    refresh: function (event) {
        event.preventDefault();
        var value = event.target.value;
        var that = this;
        $.get("http://localhost:8080/autocomplete?word=" + value, function (data, status, xhr) {
            if (xhr.status == 200 && data != null) {
                that.setState({words: data});
            }
        });
    },

    render: function () {
        if (this.state.words != null) {
            return (
                elm("div", null,
                    elm("input", {onChange: this.refresh, type: "text", list: "words"}, null),
                    elm("datalist", {id: "words"}, this.state.words.map(function (word) {
                            return elm("option", {value: word}, word);
                        })
                    )
                )
            );
        }
        else {
            return (
                elm("div", null,
                    elm("input", {onChange: this.refresh, type: "text", list: "words"}, null),
                    elm("datalist", {id: "words"}, null)
                )
            );
        }
    }


});


ReactDOM.render(
    elm(Autocomplete, null, null),
    document.getElementById('complete')
);